package org.sopt.seonyakServer.domain.appointment.service;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.sopt.seonyakServer.domain.appointment.dto.AppointmentAcceptRequest;
import org.sopt.seonyakServer.domain.appointment.dto.AppointmentRejectRequest;
import org.sopt.seonyakServer.domain.appointment.dto.AppointmentRequest;
import org.sopt.seonyakServer.domain.appointment.model.Appointment;
import org.sopt.seonyakServer.domain.appointment.model.AppointmentStatus;
import org.sopt.seonyakServer.domain.appointment.repository.AppointmentRepository;
import org.sopt.seonyakServer.domain.member.model.Member;
import org.sopt.seonyakServer.domain.member.repository.MemberRepository;
import org.sopt.seonyakServer.domain.senior.model.Senior;
import org.sopt.seonyakServer.domain.senior.repository.SeniorRepository;
import org.sopt.seonyakServer.global.auth.PrincipalHandler;
import org.sopt.seonyakServer.global.exception.enums.ErrorType;
import org.sopt.seonyakServer.global.exception.model.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final SeniorRepository seniorRepository;
    private final MemberRepository memberRepository;
    private final PrincipalHandler principalHandler;

    @Transactional
    public void postAppointment(AppointmentRequest appointmentRequest) {
        Member member = memberRepository.findMemberByIdOrThrow(principalHandler.getUserIdFromPrincipal());
        Senior senior = seniorRepository.findSeniorByIdOrThrow(appointmentRequest.seniorId());
        if (member.getId().equals(senior.getId())) {
            throw new CustomException(ErrorType.SAME_MEMBER_APPOINTMENT_ERROR);
        }
        Appointment appointment = Appointment.createAppointment(
                member,
                senior,
                AppointmentStatus.PENDING,
                appointmentRequest.timeList(),
                appointmentRequest.topic(),
                appointmentRequest.personalTopic()
        );
        appointmentRepository.save(appointment);
    }

    @Transactional
    public void acceptAppointment(AppointmentAcceptRequest appointmentAcceptRequest) {
        Appointment appointment = appointmentRepository.findAppointmentByIdOrThrow(
                appointmentAcceptRequest.appointmentId());
        Member member = memberRepository.findMemberByIdOrThrow(principalHandler.getUserIdFromPrincipal());

        // 약속의 선배ID와 토크ID가 일치하지 않는 경우
        if (!Objects.equals(member.getId(), appointment.getSenior().getId())) {
            throw new CustomException(ErrorType.NOT_AUTHORIZATION_ACCEPT);
        }

        appointment.acceptAppointment(
                appointmentAcceptRequest.timeList(),
                appointmentAcceptRequest.googleMeetLink()
        );
        appointmentRepository.save(appointment);
    }

    @Transactional
    public void rejectAppointment(AppointmentRejectRequest appointmentRejectRequest) {
        Appointment appointment = appointmentRepository.findAppointmentByIdOrThrow(
                appointmentRejectRequest.appointmentId());
        Member member = memberRepository.findMemberByIdOrThrow(principalHandler.getUserIdFromPrincipal());

        // 약속의 선배ID와 토크ID가 일치하지 않는 경우
        if (!Objects.equals(member.getId(), appointment.getSenior().getId())) {
            throw new CustomException(ErrorType.NOT_AUTHORIZATION_REJECT);
        }

        appointment.rejectAppointment(
                appointmentRejectRequest.rejectReason(),
                appointmentRejectRequest.rejectDetail()
        );
        appointmentRepository.save(appointment);
    }
}