package API;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RelatedArtists {

    public static List<String> getRelatedArtists(String artistID) {

        //environmental vars for API keys
        Dotenv dotenv = Dotenv.load();
        String APIKEY = dotenv.get("APIKEY");
        String APIHOST = dotenv.get("APIHOST");

      List<String> relatedArtists = new ArrayList<String>();

        HttpRequest rel = HttpRequest.newBuilder()
                .uri(URI.create("https://spotify23.p.rapidapi.com/artist_related/?id=" + artistID))
                .header("X-RapidAPI-Key", APIKEY)
                .header("X-RapidAPI-Host", APIHOST)
                .header("accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> res;

        ObjectMapper mapper = new ObjectMapper();
        try {
            res = HttpClient.newHttpClient().send(rel, HttpResponse.BodyHandlers.ofString());
            JsonNode artistJsonNode = mapper.readTree(res.body());
            for (JsonNode jsonNode : artistJsonNode) {
                for (JsonNode artist : jsonNode) {

                    String name = artist.get("name").toString();
                    relatedArtists.add(name);
//                    System.out.println(name);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return relatedArtists;
    }

}
