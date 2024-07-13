package org.sopt.seonyakServer.domain.appointment.controller;


import lombok.RequiredArgsConstructor;
import org.sopt.seonyakServer.domain.appointment.dto.AppointmentRequest;
import org.sopt.seonyakServer.domain.appointment.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/appoinment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("")
    public ResponseEntity<Void> postAppointment(
            @RequestBody final AppointmentRequest appointmentRequest
    ) {
        appointmentService.postAppointment(appointmentRequest);
        return ResponseEntity.ok().build();
    }
}
