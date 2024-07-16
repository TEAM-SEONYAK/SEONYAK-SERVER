package org.sopt.seonyakServer.domain.appointment.controller;


import lombok.RequiredArgsConstructor;
import org.sopt.seonyakServer.domain.appointment.dto.AppointmentAcceptRequest;
import org.sopt.seonyakServer.domain.appointment.dto.AppointmentDetailResponse;
import org.sopt.seonyakServer.domain.appointment.dto.AppointmentRejectRequest;
import org.sopt.seonyakServer.domain.appointment.dto.AppointmentRequest;
import org.sopt.seonyakServer.domain.appointment.dto.GoogleMeetLinkResponse;
import org.sopt.seonyakServer.domain.appointment.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/appointment")
    public ResponseEntity<Void> postAppointment(
            @RequestBody AppointmentRequest appointmentRequest
    ) {
        appointmentService.postAppointment(appointmentRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/appointment/accept")
    public ResponseEntity<Void> acceptAppointment(
            @RequestBody AppointmentAcceptRequest appointmentAcceptRequest
    ) {
        appointmentService.acceptAppointment(appointmentAcceptRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/appointment/reject")
    public ResponseEntity<Void> rejectAppointment(
            @RequestBody AppointmentRejectRequest appointmentRejectRequest
    ) {
        appointmentService.rejectAppointment(appointmentRejectRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/google-meet/{appointmentId}")
    public ResponseEntity<GoogleMeetLinkResponse> getGoogleMeetLink(
            @PathVariable final Long appointmentId
    ) {
        return ResponseEntity.ok(appointmentService.getGoogleMeetLink(appointmentId));
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<AppointmentDetailResponse> getAppointmentDetail(
            @PathVariable final Long appointmentId
    ) {
        return ResponseEntity.ok(appointmentService.getAppointmentDetail(appointmentId));
    }
}
