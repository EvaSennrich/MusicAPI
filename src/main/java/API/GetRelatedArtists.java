package API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class GetRelatedArtists {

    public List<String> getRelatedArtists() {

        //environmental vars for API keys
        Dotenv dotenv = Dotenv.load();
        String APIKEY = dotenv.get("APIKEY");
        String APIHOST = dotenv.get("APIHOST");

        java.util.List<String> relatedArtists = new ArrayList<String>();

        HttpRequest rel = HttpRequest.newBuilder()
                .uri(URI.create("https://spotify23.p.rapidapi.com/artist_related/?id=4q3ewBCX7sLwd24euuV69X"))
                .header("X-RapidAPI-Key", APIKEY)
                .header("X-RapidAPI-Host", APIHOST)
                .header("accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> res = null;

//        try {
//            res = HttpClient.newHttpClient().send(rel, HttpResponse.BodyHandlers.ofString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

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