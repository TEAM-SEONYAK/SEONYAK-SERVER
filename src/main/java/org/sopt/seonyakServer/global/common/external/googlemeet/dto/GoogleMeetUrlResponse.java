package org.sopt.seonyakServer.global.common.external.googlemeet.dto;

public record GoogleMeetUrlResponse(
        String googleMeet
) {
    public static GoogleMeetUrlResponse of(
            final String googleMeet
    ) {
        return new GoogleMeetUrlResponse(googleMeet);
    }
}
