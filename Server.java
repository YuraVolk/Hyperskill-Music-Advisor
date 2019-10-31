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
    private SpotifyModel model;

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
        api = new SpotifyApi.Builder()
                .setClientId(clientID)
                .setClientSecret(clientSecret)
                .setRedirectUri(redirectURI)
                .build();

        final ClientCredentialsRequest clientCredentialsRequest = api.clientCredentials()
                .build();

        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            setAccessToken(clientCredentials.getAccessToken());


            model = new SpotifyModel(api);
            return true;
        } catch (IOException | SpotifyWebApiException e) {
            return false;
        }
    }

    private void setAccessToken(String accessToken) {
        api.setAccessToken(accessToken);
    }

    void getCategories() {
        model.getCategories();
    }

    void getCategory(String category) {
        model.getCategory(category.toLowerCase().replaceAll("\\s+", ""));
    }

    void getFeatured() {
        model.getFeatured();
    }

    void getNew() {
        model.getNew();
    }

    void nullifyPage() {
        model.nullifyPage();
    }

    void previousPage() {
        model.previousPage();
    }

    void nextPage() {
        model.nextPage();
    }
}
