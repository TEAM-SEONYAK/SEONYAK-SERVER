package org.sopt.seonyakServer.domain.appointment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.seonyakServer.domain.appointment.dto.AppointmentDetailRequest;
import org.sopt.seonyakServer.domain.appointment.dto.AppointmentDetailResponse;
import org.sopt.seonyakServer.domain.appointment.dto.AppointmentRequest;
import org.sopt.seonyakServer.domain.appointment.model.Appointment;
import org.sopt.seonyakServer.domain.appointment.model.AppointmentStatus;
import org.sopt.seonyakServer.domain.appointment.model.JuniorInfo;
import org.sopt.seonyakServer.domain.appointment.model.SeniorInfo;
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

@Slf4j
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

    @Transactional(readOnly = true)
    public AppointmentDetailResponse getAppointmentDetail(
            final AppointmentDetailRequest appointmentDetailRequest
    ) {
        Long userId = memberRepository.findMemberByIdOrThrow(principalHandler.getUserIdFromPrincipal()).getId();
        Long memberId = appointmentRepository.findMemberIdById(appointmentDetailRequest.appointmentId());
        Long seniorId = appointmentRepository.findSeniorIdById(appointmentDetailRequest.appointmentId());

        log.warn(userId + " " + memberId + " " + seniorId);

        if (!userId.equals(memberId) && !userId.equals(seniorId)) {
            throw new CustomException(ErrorType.NOT_MEMBERS_APPOINTMENT_ERROR);
        }

        Appointment appointment = appointmentRepository.findAppointmentByIdOrThrow(
                appointmentDetailRequest.appointmentId()
        );

        JuniorInfo juniorInfo = JuniorInfo.create(
                appointment.getMember().getNickname(),
                appointment.getMember().getUnivName(),
                appointment.getMember().getField(),
                appointment.getMember().getDepartment()
        );

        SeniorInfo seniorInfo = SeniorInfo.create(
                appointment.getSenior().getMember().getNickname(),
                appointment.getSenior().getMember().getImage(),
                appointment.getSenior().getCompany(),
                appointment.getSenior().getMember().getField(),
                appointment.getSenior().getPosition(),
                appointment.getSenior().getDetailPosition(),
                appointment.getSenior().getLevel()
        );

        return new AppointmentDetailResponse(
                juniorInfo,
                seniorInfo,
                appointment.getTopic(),
                appointment.getPersonalTopic(),
                appointment.getTimeList()
        );
    }
}
