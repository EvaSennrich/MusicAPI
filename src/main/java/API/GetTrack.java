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

public class GetTrack {

    public List<String> getTracks() {

        //environmental vars for API keys
        Dotenv dotenv = Dotenv.load();
        String APIKEY = dotenv.get("APIKEY");
        String APIHOST = dotenv.get("APIHOST");

        java.util.List<String> Tracks = new ArrayList<String>();

        HttpRequest trackRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://spotify23.p.rapidapi.com/artist_related/?id=4q3ewBCX7sLwd24euuV69X"))
                .header("X-RapidAPI-Key", APIKEY)
                .header("X-RapidAPI-Host", APIHOST)
                .header("accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> res = null;



        ObjectMapper mapper = new ObjectMapper();
        try {
            res = HttpClient.newHttpClient().send(trackRequest, HttpResponse.BodyHandlers.ofString());
            JsonNode trackJsonNode = mapper.readTree(res.body());
            for (JsonNode jsonNode : trackJsonNode) {
                for (JsonNode track : jsonNode) {

                    String name = track.get("name").toString();
                    Tracks.add(name);
                   System.out.println(name);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Tracks;
    }

}



