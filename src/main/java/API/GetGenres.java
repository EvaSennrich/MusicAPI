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


    public static List<String> getGenres(String ID) throws IOException, InterruptedException {
        // Environmental vars for API keys
        Dotenv dotenv = Dotenv.load();
        String APIKEY = dotenv.get("APIKEY");
        String APIHOST = dotenv.get("APIHOST");

        // Create the request URI with the artist ID as a query parameter
        URI uri = URI.create("https://spotify23.p.rapidapi.com/genre_view/?id=" + ID + "&content_limit=10&limit=10");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("X-RapidAPI-Key", APIKEY)
                .header("X-RapidAPI-Host", APIHOST)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());


        List<String> results = new ArrayList<>();
        JSONObject obj = new JSONObject(response);
        JSONArray genres = obj.getJSONObject("content").getJSONArray("items");

        for (int i = 0; i < genres.length(); i++) {
            String genreName = genres.getJSONObject(i).getString("name");
            System.out.println(genreName);
            results.add(genreName);
            System.out.println(results);
        }
        return results;


    }//end of getGenres()


}//end of GetGenres class


