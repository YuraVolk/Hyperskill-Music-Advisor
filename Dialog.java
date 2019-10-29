package advisor;

import java.util.*;

/**
 * Client Id: a7bf6e4aeec14099bedf9d0e4fc9f351
 * Client Secret: d6f13f9fc62e49b781d63a02ebcf51c2
 * Redirect URI: https://music.panel.authorize.com
 * Authorize link:
 * https://accounts.spotify.com/authorize?client_id=a7bf6e4aeec14099bedf9d0e4fc9f351&redirect_uri=https://music.panel.authorize.com&response_type=code
 * Acess Token:
 * AQBIlhTkz0fOnfRRwCJQN-1O6Zh-B25vpQe18KTZV2luunGuI-FeDzH-00kOpa47Y_2MjZxa5MmvgRdLrUFwIFzdQ64WHECnCKMLjTwPb9H_cjC8h0w4r2WKSxNzcnp5XwNfsVAhfCpuJF_dSx9YWcrGxz0_pHMqkiMX1s9NtZIyomBz9O9TvPxGU1eH-52MNQxYZLCcov8B
 */

class Dialog {
    private String choice = "";
    private final Scanner scanner = new Scanner(System.in);



    private ArrayList<String> generateArrayList(String... val) { //TODO Replace with model call
        return new ArrayList<>(Arrays.asList(val));
    }

    private void printReleases(ArrayList<String> releases) {
        releases.forEach(System.out :: println);
    }

    private void printReleases(Map<String, ArrayList<String>> releases) {
        releases.forEach((key, value) -> System.out.println(key + " " + value));
    }

    private void getNewReleases() {
        System.out.println("---NEW RELEASES---");
        Map<String, ArrayList<String>> releases =  new HashMap<>();
        releases.put("Mountains", generateArrayList("Sia", "Diplo", "Labrinth"));
        releases.put("Runaway", generateArrayList("Lil Peep"));
        releases.put("The Greatest Show", generateArrayList("Panic! At The Disco"));
        releases.put("All Out Life", generateArrayList("Slipknot"));
        printReleases(releases);
    }

    private void getFeatured() {
        System.out.println("---FEATURED---");
        ArrayList<String> playlists = new ArrayList<>();
        playlists.add("Mellow Morning");
        playlists.add("Wake Up and Smell the Coffee");
        playlists.add("Monday Motivation");
        playlists.add("Songs to Sing in the Shower");
        printReleases(playlists);
    }

    private void getCategories() {
        System.out.println("---CATEGORIES---");
        ArrayList<String> playlists = new ArrayList<>();
        playlists.add("Top lists");
        playlists.add("Pop");
        playlists.add("Mood");
        playlists.add("Latin");
        printReleases(playlists);
    }

    private void getPlaylistReleases(String category) {
        System.out.printf("---%S PLAYLISTS---\n", category);
        //TODO Call controller. Controller will call model
        ArrayList<String> playlists = new ArrayList<>();
        playlists.add("Walk Like A Badass");
        playlists.add("Rage Beats");
        playlists.add("Arab Mood Booster");
        playlists.add("Sunday Stroll");
        printReleases(playlists);
    }

    void startProgram() {
        final String clientId = "a7bf6e4aeec14099bedf9d0e4fc9f351";
        final String redirectURI = "https://music.panel.authorize.com";
        boolean isSigned = false;

        while(!choice.equals("exit")) {
            choice = scanner.next();
            if (choice.equals("auth")) {
                System.out.printf("https://accounts.spotify.com/authorize?client_id=%s" +
                        "&redirect_uri=%s&response_type=code", clientId, redirectURI);
                isSigned = true;
                System.out.println("---SUCCESS---");
            }
            if (!isSigned) {
                System.out.println("Please, provide access for application.");
                continue;
            }
            switch (choice) {
                case "new":
                    getNewReleases();
                    break;
                case "featured":
                    getFeatured();
                    break;
                case "categories":
                    getCategories();
                    break;
                case "playlists":
                    getPlaylistReleases(scanner.next());
            }
        }

        System.out.println("---GOODBYE!---");
    }
}

