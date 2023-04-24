package API;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class GetBio {

    public static String getBio(String ID) throws IOException, InterruptedException {
        // Environmental vars for API keys
        Dotenv dotenv = Dotenv.load();
        String APIKEY = dotenv.get("APIKEY");
        String APIHOST = dotenv.get("APIHOST");

        // Create the request URI with the artist ID as a query parameter
        URI uri = URI.create("https://spotify23.p.rapidapi.com/artist_overview/?id=" + ID);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("X-RapidAPI-Key", APIKEY)
                .header("X-RapidAPI-Host", APIHOST)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println("getBio res" + response.body());

        return response.body();

    }//end of getGenres()


    public static List<String> extractBiosFromResponse(String response) {
        List<String> result = new ArrayList<>();
        JSONObject obj = new JSONObject(response);
        JSONObject bio = obj.getJSONObject("data").getJSONObject("artist").getJSONObject("profile").getJSONObject("biography");

        for (int i = 0; i < bio.length(); i++) {
            String name = bio.get("text").toString();
            String trimmedBio = name.substring(0, Math.min(name.length(), 855));
            String correctedBio = trimmedBio.replaceAll("<a[^>]*>.*?</a>", "");

            printFormattedBio(correctedBio, 100);

            result.add(correctedBio);
        }

        return result;
    }



    public static void printFormattedBio(String correctedBio, int lineLength) {
        int currentIndex = 0;
        int bioLength = correctedBio.length();
        StringBuilder sb = new StringBuilder();

        while (currentIndex < bioLength) {
            int endIndex = Math.min(currentIndex + lineLength, bioLength);
            String line = correctedBio.substring(currentIndex, endIndex);
            sb.append(line).append("\n");
            currentIndex += lineLength;
        }

        System.out.println("=======================================================================================================");
        System.out.println("                                     Artist Biography \n");
        System.out.println("=======================================================================================================");
        System.out.println(sb);

    }


}//end of GetGenres class

