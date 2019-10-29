package advisor;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.special.FeaturedPlaylists;
import com.wrapper.spotify.model_objects.specification.*;

import com.wrapper.spotify.requests.data.browse.GetCategorysPlaylistsRequest;
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
            if (categoryPaging.length < 4) {
                for (int i = 0; i < categoryPaging.length; i++) {
                    System.out.println(categoryPaging[i].getName());
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    System.out.println(categoryPaging[i].getName());
                }
            }

        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Internal server error.");
        }
    }

    void getCategory(String category) {
        final GetCategorysPlaylistsRequest getCategoryRequest = api.getCategorysPlaylists(category)
                .build();

        try {
            final PlaylistSimplified[] playlists = getCategoryRequest.execute().getItems();

            System.out.printf("---%S PLAYLISTS---\n", category);
            if (playlists.length == 0) {
                System.out.println("No playlists with this category exists.");
            } else {
                if (playlists.length < 4) {
                    for (int i = 0; i < playlists.length; i++) {
                        System.out.println(playlists[i].getName());
                    }
                } else {
                    for (int i = 0; i < 4; i++) {
                        System.out.println(playlists[i].getName());
                    }
                }

            }
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    void getFeatured() {

        final GetListOfFeaturedPlaylistsRequest getListOfFeaturedPlaylistsRequest = api
                .getListOfFeaturedPlaylists().build();

        try {
            final FeaturedPlaylists featuredPlaylists = getListOfFeaturedPlaylistsRequest.execute();
            System.out.println("---FEATURED---");
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
            for (int i = 0; i < 5; i++) {
                releases.put(albumSimplifiedPaging[i].getName().replaceAll("\\[|\\]", "")
                ,arrayToList(albumSimplifiedPaging[0].getArtists()));
            }
            System.out.println("---NEW RELEASES---");
            releases.forEach((key, value) -> System.out.println(key + " " + value));
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Internal server error.");
        }
    }
}
