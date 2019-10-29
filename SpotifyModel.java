package advisor;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.special.FeaturedPlaylists;
import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.Category;

import com.wrapper.spotify.requests.data.browse.GetListOfCategoriesRequest;
import com.wrapper.spotify.requests.data.browse.GetListOfFeaturedPlaylistsRequest;
import com.wrapper.spotify.requests.data.browse.GetListOfNewReleasesRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class SpotifyModel {
    private SpotifyApi api;

    SpotifyModel(SpotifyApi api) {
        this.api = api;
    }


    void getCategories() {
        final GetListOfCategoriesRequest list = api.getListOfCategories().build();
        try {
            final Category[] categoryPaging = list.execute().getItems();
            System.out.println("---CATEGORIES---");
            for (int i = 0; i < 4; i++) {
                System.out.println(categoryPaging[i].getName());
            }
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Internal server error.");
        }
    }

    void getCategory(String category) {

    }

    void getFeatured() {
        final GetListOfFeaturedPlaylistsRequest getListOfFeaturedPlaylistsRequest = api
                .getListOfFeaturedPlaylists().build();

        try {
            final FeaturedPlaylists featuredPlaylists = getListOfFeaturedPlaylistsRequest.execute();
            for (int i = 0; i < 5; i++) {
                System.out.println(featuredPlaylists.getPlaylists().getItems()[i].getName());
            }
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Internal server error.");
        }
    }

    private ArrayList<String> arrayToList(ArtistSimplified[] authors) {
        ArrayList<String> arr = new ArrayList<>();
        for (int i = 0; i < authors.length; i++) {
            arr.add(authors[i].getName());
        }
        return arr;
    }

    void getNew() {
        final GetListOfNewReleasesRequest getListOfNewReleasesRequest = api.getListOfNewReleases()
                .build();

        try {
            final AlbumSimplified[] albumSimplifiedPaging = getListOfNewReleasesRequest.execute().getItems();
            Map<String, ArrayList<String>> releases =  new HashMap<>();
            System.out.println("Total: " + albumSimplifiedPaging[0].getName());
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Internal server error.");
        }
    }
}
