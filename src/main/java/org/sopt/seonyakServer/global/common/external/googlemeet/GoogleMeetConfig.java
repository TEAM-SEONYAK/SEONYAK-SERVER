package org.sopt.seonyakServer.global.common.external.googlemeet;

import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.apps.meet.v2.SpacesServiceSettings;
import com.google.auth.Credentials;
import com.google.auth.oauth2.ClientId;
import com.google.auth.oauth2.DefaultPKCEProvider;
import com.google.auth.oauth2.TokenStore;
import com.google.auth.oauth2.UserAuthorizer;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.sopt.seonyakServer.global.exception.enums.ErrorType;
import org.sopt.seonyakServer.global.exception.model.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Configuration
@Slf4j
public class GoogleMeetConfig {

    @Value("${google.credentials.oauth2.callback.uri}")
    private String callbackUri;

    @Value("${google.credentials.tokens.directory.path}")
    private String tokensDirectoryPath;

    @Value("${google.credentials.scopes}")
    private List<String> scopes;

    @Value("${aws-property.s3-bucket-name}")
    private String bucketName;

    private static final String USER = "default";

    @Bean
    public TokenStore tokenStore(S3Client s3Client) {
        return new TokenStore() {
            private String s3KeyFor(String id) {
                return tokensDirectoryPath + "/" + id + ".json";
            }

            @Override
            public String load(String id) throws IOException {
                String key = s3KeyFor(id);
                try {
                    InputStream inputStream = s3Client.getObject(GetObjectRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .build());
                    return new String(inputStream.readAllBytes());
                } catch (Exception e) {
                    log.error("Failed to load token from S3: " + e.getMessage(), e);
                    return null;
                }
            }

            @Override
            public void store(String id, String token) throws IOException {
                String key = s3KeyFor(id);
                try {
                    s3Client.putObject(PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .build(), RequestBody.fromString(token));
                } catch (Exception e) {
                    log.error("Failed to store token in S3: " + e.getMessage(), e);
                }
            }

            @Override
            public void delete(String id) throws IOException {
                String key = s3KeyFor(id);
                try {
                    s3Client.deleteObject(DeleteObjectRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .build());
                } catch (Exception e) {
                    log.error("Failed to delete token from S3: " + e.getMessage(), e);
                }
            }
        };
    }

    @Bean
    public UserAuthorizer userAuthorizer(TokenStore tokenStore) throws IOException {
        ClassPathResource resource = new ClassPathResource("json/credentials.json");
        try (InputStream in = resource.getInputStream()) {
            ClientId clientId = ClientId.fromStream(in);
            return UserAuthorizer.newBuilder()
                    .setClientId(clientId)
                    .setCallbackUri(URI.create(callbackUri))
                    .setScopes(scopes)
                    .setPKCEProvider(new DefaultPKCEProvider() {
                        @Override
                        public String getCodeChallenge() {
                            return super.getCodeChallenge().split("=")[0];
                        }
                    })
                    .setTokenStore(tokenStore)
                    .build();
        }
    }

    @Bean
    public LocalServerReceiver localServerReceiver() {
        return new LocalServerReceiver.Builder().setPort(8081).build();
    }

    @Bean
    public SpacesServiceSettings spacesServiceSettings(Credentials credentials) throws IOException {
        return SpacesServiceSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();
    }

    @Bean
    public Credentials credentials(UserAuthorizer userAuthorizer, LocalServerReceiver localServerReceiver)
            throws Exception {
        // UserAuthorizer를 사용하여 지정된 사용자의 Credentials를 가져옴
        Credentials credentials = userAuthorizer.getCredentials(USER);
        if (credentials != null) {
            return credentials;
        } else {
            throw new CustomException(ErrorType.GET_GOOGLE_AUTHORIZER_ERROR);
        }
    }
}
