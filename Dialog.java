package advisor;

import java.util.*;

/**
 * Client Id: a7bf6e4aeec14099bedf9d0e4fc9f351
 * Client Secret: d6f13f9fc62e49b781d63a02ebcf51c2
 * Redirect URI: https://music.panel.authorize.com
 */

class Dialog {
    private String choice = "";
    private final Scanner scanner = new Scanner(System.in);

    private void previousOption(String previousChoice, Server server) {
        switch (previousChoice) {
            case "new":
                server.getNew();
                break;
            case "featured":
                server.getFeatured();
                break;
            case "categories":
                server.getCategories();
                break;
            case "playlists":
                server.getCategory(scanner.nextLine());
                break;
        }
    }


    void startProgram() {
        final String clientId = "a7bf6e4aeec14099bedf9d0e4fc9f351";
        final String redirectURI = "https://music.panel.authorize.com";
        final String secret = "d6f13f9fc62e49b781d63a02ebcf51c2";

        boolean isSigned = false;

        Server server = new Server(redirectURI, clientId, secret);

        String previousChoice = "";
        while(!choice.equals("exit")) {
            choice = scanner.next();

            if (choice.equals("auth")) {
                System.out.println("making http request for access_token...");
                isSigned = server.requestAccessToken();
                if (!isSigned) {
                    System.out.println("Internal server error. Please reload the app.");
                } else {
                    System.out.println("Success!");
                }

            }

            if (!isSigned) {
                System.out.println("Please, provide access for application.");
                continue;
            }

            switch (choice) {
                case "new":
                    server.nullifyPage();
                    server.getNew();
                    break;
                case "featured":
                    server.nullifyPage();
                    server.getFeatured();
                    break;
                case "categories":
                    server.nullifyPage();
                    server.getCategories();
                    break;
                case "playlists":
                    server.nullifyPage();
                    server.getCategory(scanner.nextLine());
                    break;
                case "next":
                    server.nextPage();
                    previousOption(previousChoice, server);
                    break;
                case "prev":
                    server.previousPage();
                    previousOption(previousChoice, server);
                    break;
            }
            if (!choice.equals("next") && !choice.equals("prev")) {
                previousChoice = choice;
            }
        }

        System.out.println("---GOODBYE!---");
    }
}


