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

public class GetGenres {


    public static String getGenres(String artistID) throws IOException, InterruptedException {
        // Environmental vars for API keys
        Dotenv dotenv = Dotenv.load();
        String APIKEY = dotenv.get("APIKEY");
        String APIHOST = dotenv.get("APIHOST");

        // Create the request URI with the artist ID as a query parameter
        URI uri = URI.create("https://spotify23.p.rapidapi.com/artists/?ids=" + artistID);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("X-RapidAPI-Key", APIKEY)
                .header("X-RapidAPI-Host", APIHOST)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();

    }//end of getGenres()


    public static List<String> extractGenresFromResponse(String response) {
        List<String> result = new ArrayList<>();
        JSONObject obj = new JSONObject(response);
        JSONArray genres = obj.getJSONArray("artists");


        JSONArray genreName = genres.getJSONObject(0).getJSONArray("genres");
        for (int i = 0; i < genreName.length(); i++) {
            result.add((String) genreName.get(i));
        }

        return result;
    }

}//end of GetGenres class


