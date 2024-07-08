package org.sopt.seonyakServer.global.common.external.googlemeet;

import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.apps.meet.v2.SpacesServiceSettings;
import com.google.auth.Credentials;
import com.google.auth.oauth2.ClientId;
import com.google.auth.oauth2.DefaultPKCEProvider;
import com.google.auth.oauth2.TokenStore;
import com.google.auth.oauth2.UserAuthorizer;
import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleMeetConfig {

    @Value("${google.credentials.file.path}")
    private String credentialsFilePath;

    @Value("${google.credentials.oauth2.callback.uri}")
    private String callbackUri;

    @Value("${google.credentials.tokens.directory.path}")
    private String tokensDirectoryPath;

    @Value("${google.credentials.scopes}")
    private List<String> scopes;

    private static final String USER = "default";

    @Bean
    public TokenStore tokenStore() {
        return new TokenStore() {
            private Path pathFor(String id) {
                return Paths.get(".", tokensDirectoryPath, id + ".json");
            }

            @Override
            public String load(String id) throws IOException {
                if (!Files.exists(pathFor(id))) {
                    return null;
                }
                return Files.readString(pathFor(id));
            }

            @Override
            public void store(String id, String token) throws IOException {
                Files.createDirectories(Paths.get(".", tokensDirectoryPath));
                Files.writeString(pathFor(id), token);
            }

            @Override
            public void delete(String id) throws IOException {
                if (!Files.exists(pathFor(id))) {
                    return;
                }
                Files.delete(pathFor(id));
            }
        };
    }

    @Bean
    public UserAuthorizer userAuthorizer(TokenStore tokenStore) throws IOException {
        try (InputStream in = getClass().getResourceAsStream(credentialsFilePath)) {
            if (in == null) {
                throw new IOException("Resource not found: " + credentialsFilePath);
            }
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
        return new LocalServerReceiver.Builder().setPort(8082).build();
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
        Credentials credentials = userAuthorizer.getCredentials(USER);
        if (credentials != null) {
            return credentials;
        }
        URL authorizationUrl = userAuthorizer.getAuthorizationUrl(USER, "", null);
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(authorizationUrl.toURI());
        } else {
            System.out.printf("Open the following URL to authorize access: %s\n", authorizationUrl.toExternalForm());
        }
        String code = localServerReceiver.waitForCode();
        return userAuthorizer.getAndStoreCredentialsFromCode(USER, code, URI.create(callbackUri));
    }
}
