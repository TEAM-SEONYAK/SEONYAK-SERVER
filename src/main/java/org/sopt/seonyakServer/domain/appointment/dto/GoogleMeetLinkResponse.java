package org.sopt.seonyakServer.domain.appointment.dto;

public record GoogleMeetLinkResponse(
        String googleMeetLink
) {
    public static GoogleMeetLinkResponse of(final String googleMeetLink) {
        return new GoogleMeetLinkResponse(googleMeetLink);
    }
}
