package advisor;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

class Server {
    private URI redirectURI;
    private final String clientID;
    private final String clientSecret;

    private SpotifyApi api;

    Server(String redirectURI, String clientID, String clientSecret) {
        try {
            this.redirectURI = new URI(redirectURI);
        } catch (URISyntaxException e) {
            System.exit(1);
        }

        this.clientID = clientID;
        this.clientSecret = clientSecret;
    }


    boolean requestAccessToken() {
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientID)
                .setClientSecret(clientSecret)
                .setRedirectUri(redirectURI)
                .build();

        final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
                .build();

        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());

            api = spotifyApi;
            return true;
        } catch (IOException | SpotifyWebApiException e) {
            return false;
        }
    }

    String getAccessToken() {
        return api.getAccessToken();
    }
}
