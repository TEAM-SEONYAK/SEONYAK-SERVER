package org.sopt.seonyakServer.global.common.external.googlemeet;

import com.google.apps.meet.v2.CreateSpaceRequest;
import com.google.apps.meet.v2.Space;
import com.google.apps.meet.v2.SpacesServiceClient;
import com.google.apps.meet.v2.SpacesServiceSettings;
import lombok.RequiredArgsConstructor;
import org.sopt.seonyakServer.global.common.external.googlemeet.dto.GoogleMeetUrlResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleMeetService {
    private final SpacesServiceSettings spacesServiceSettings;

    public GoogleMeetUrlResponse createMeetingSpace() throws Exception {
        SpacesServiceClient spacesServiceClient = SpacesServiceClient.create(spacesServiceSettings);
        CreateSpaceRequest request = CreateSpaceRequest.newBuilder()
                .setSpace(Space.newBuilder().build())
                .build();
        Space response = spacesServiceClient.createSpace(request);
        return GoogleMeetUrlResponse.of(response.getMeetingUri());
    }
}