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
    private int currentPage = 0;

    SpotifyModel(SpotifyApi api) {
        this.api = api;
    }

    private String processLink(String link, String subCategory) {
        String processedLink = link.replace("api.spotify", "open.spotify");
        switch (subCategory) {
            case "albums":
                processedLink = processedLink.replace("albums", "album");
                break;
            case "playlists":
            default:
                processedLink = processedLink.replace("playlists", "playlist");
                break;
        }

        processedLink = processedLink.replace("v1/", "");
        return processedLink;
    }

    void getCategories() {
        if (currentPage < 0) {
            currentPage++;
            System.out.println("No more pages.");
            return;
        }

        final int categories = 40;
        final int limit = 5;

        if (currentPage + 1 > categories / limit) {
            System.out.println("No more pages.");
            currentPage--;
            return;
        }
        final GetListOfCategoriesRequest list = api.getListOfCategories().offset(currentPage * limit).limit(limit).build();
        try {
            final Category[] categoryPaging = list.execute().getItems();

            System.out.println("---CATEGORIES---");
            for (int i = 0; i < categoryPaging.length; i++) {
                System.out.println(categoryPaging[i].getName());
            }
            System.out.printf("---PAGE %s of %s---\n", currentPage + 1, categories / limit);
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
                if (playlists.length < 5) {
                    for (int i = 0; i < playlists.length; i++) {
                        System.out.println(playlists[i].getName());
                        System.out.println(processLink(playlists[i].getHref(),
                                "playlists") + "\n");
                    }
                } else {
                    for (int i = 0; i < 5; i++) {
                        System.out.println(playlists[i].getName());
                        System.out.println(processLink(playlists[i].getHref(),
                                "playlists") + "\n");
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
                System.out.println(processLink(featuredPlaylists.getPlaylists()
                        .getItems()[i].getHref() + "\n", "playlists"));
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
        if (currentPage < 0) {
            currentPage++;
            System.out.println("No more pages.");
            return;
        }

        final int songs = 25;
        final int limit = 5;

        if (currentPage + 1 > songs / limit) {
            System.out.println("No more pages.");
            currentPage--;
            return;
        }

        final GetListOfNewReleasesRequest getListOfNewReleasesRequest = api.getListOfNewReleases()
                .offset(currentPage * limit).limit(limit).build();

        try {
            final AlbumSimplified[] albumSimplifiedPaging = getListOfNewReleasesRequest.execute().getItems();
            Map<String, ArrayList<String>> releases =  new HashMap<>();
            ArrayList<String> links = new ArrayList<>();
            for (int i = 0; i < albumSimplifiedPaging.length; i++) {
                releases.put(albumSimplifiedPaging[i].getName().replaceAll("\\[|\\]", "")
                ,arrayToList(albumSimplifiedPaging[0].getArtists()));
                links.add(albumSimplifiedPaging[i].getHref());
            }
            System.out.println("---NEW RELEASES---");
            for (int i = 0; i < releases.size(); i++) {
                System.out.println(releases.keySet().toArray()[i]);
                System.out.println(releases.values().toArray()[i]);
                System.out.println(processLink(links.get(i), "albums") + "\n");
            }
            System.out.printf("---PAGE %s of %s---\n", currentPage + 1, songs / limit);

        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Internal server error.");
        }
    }

    void nullifyPage() {
        currentPage = 0;
    }

    void nextPage() {
        currentPage++;
    }

    void previousPage() {
        currentPage--;
    }
}
