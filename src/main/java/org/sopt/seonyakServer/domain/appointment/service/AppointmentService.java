package org.sopt.seonyakServer.domain.appointment.service;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.seonyakServer.domain.appointment.dto.AppointmentAcceptRequest;
import org.sopt.seonyakServer.domain.appointment.dto.AppointmentDetailResponse;
import org.sopt.seonyakServer.domain.appointment.dto.AppointmentRejectRequest;
import org.sopt.seonyakServer.domain.appointment.dto.AppointmentRequest;
import org.sopt.seonyakServer.domain.appointment.dto.GoogleMeetLinkResponse;
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
        Appointment appointment = Appointment.create(
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
                appointmentAcceptRequest.appointmentId()
        );
        Member member = memberRepository.findMemberByIdOrThrow(principalHandler.getUserIdFromPrincipal());

        // 약속의 선배 Id와 토큰 Id가 일치하지 않는 경우
        if (!Objects.equals(member.getId(), appointment.getSenior().getId())) {
            throw new CustomException(ErrorType.NOT_AUTHORIZATION_ACCEPT);
        }

        appointment.acceptAppointment(
                appointmentAcceptRequest.timeList(),
                appointmentAcceptRequest.googleMeetLink(),
                AppointmentStatus.SCHEDULED
        );
        appointmentRepository.save(appointment);
    }

    @Transactional
    public void rejectAppointment(AppointmentRejectRequest appointmentRejectRequest) {
        Appointment appointment = appointmentRepository.findAppointmentByIdOrThrow(
                appointmentRejectRequest.appointmentId()
        );
        Member member = memberRepository.findMemberByIdOrThrow(principalHandler.getUserIdFromPrincipal());

        // 약속의 선배 Id와 토큰 Id가 일치하지 않는 경우
        if (!Objects.equals(member.getId(), appointment.getSenior().getId())) {
            throw new CustomException(ErrorType.NOT_AUTHORIZATION_REJECT);
        }

        appointment.rejectAppointment(
                appointmentRejectRequest.rejectReason(),
                appointmentRejectRequest.rejectDetail(),
                AppointmentStatus.REJECTED
        );
        appointmentRepository.save(appointment);
    }

    @Transactional(readOnly = true)
    public GoogleMeetLinkResponse getGoogleMeetLink(Long appointmentId) {
        Long userId = memberRepository.findMemberByIdOrThrow(principalHandler.getUserIdFromPrincipal()).getId();

        Appointment appointment = appointmentRepository.findAppointmentByIdOrThrow(appointmentId);
        Long memberId = appointment.getMember().getId();
        Long seniorMemberId = appointment.getSenior().getMember().getId();

        if (!userId.equals(memberId) && !userId.equals(seniorMemberId)) {
            throw new CustomException(ErrorType.NOT_MEMBERS_APPOINTMENT_ERROR);
        }

        String googleMeetLink = appointment.getGoogleMeetLink();

        if (googleMeetLink == null || googleMeetLink.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_GOOGLE_MEET_LINK_ERROR);
        }

        return GoogleMeetLinkResponse.of(googleMeetLink);
    }

    @Transactional(readOnly = true)
    public AppointmentDetailResponse getAppointmentDetail(
            final Long appointmentId
    ) {
        Long userId = memberRepository.findMemberByIdOrThrow(principalHandler.getUserIdFromPrincipal()).getId();

        Appointment appointment = appointmentRepository.findAppointmentByIdOrThrow(appointmentId);
        Long memberId = appointment.getMember().getId();
        Long seniorMemberId = appointment.getSenior().getMember().getId();

        if (!userId.equals(memberId) && !userId.equals(seniorMemberId)) {
            throw new CustomException(ErrorType.NOT_MEMBERS_APPOINTMENT_ERROR);
        }

        JuniorInfo juniorInfo = JuniorInfo.create(
                appointment.getMember().getNickname(),
                appointment.getMember().getUnivName(),
                appointment.getMember().getField(),
                appointment.getMember().getDepartmentList().get(0)
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
                appointment.getAppointmentStatus(),
                juniorInfo,
                seniorInfo,
                appointment.getTopic(),
                appointment.getPersonalTopic(),
                appointment.getTimeList()
        );
    }
}
