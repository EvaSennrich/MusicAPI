import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {

        public static void main(String[] args) throws IOException, InterruptedException {
            //environmental vars for API keys
            Dotenv dotenv = Dotenv.load();
            String APIKEY = dotenv.get("APIKEY");
            String APIHOST = dotenv.get("APIHOST");


            //getArtist --> need to change part where is just reading `?q=bad%20bunny`
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://spotify23.p.rapidapi.com/search/?q=bad%20bunny&type=artist&offset=0&limit=1&numberOfTopResults=1"))
                    .header("X-RapidAPI-Key", APIKEY)
                    .header("X-RapidAPI-Host", APIHOST)
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

            //getRelatedArtist --> need the change id part `?id=4q3ewBCX7sLwd24euuV69X"`
//            HttpRequest rel = HttpRequest.newBuilder()
//                    .uri(URI.create("https://spotify23.p.rapidapi.com/artist_related/?id=4q3ewBCX7sLwd24euuV69X"))
//                    .header("X-RapidAPI-Key", APIKEY)
//                    .header("X-RapidAPI-Host", APIHOST)
//                    .header("accept", "application/json")
//                    .method("GET", HttpRequest.BodyPublishers.noBody())
//                    .build();
//            HttpResponse<String> res = HttpClient.newHttpClient().send(rel, HttpResponse.BodyHandlers.ofString());

            //track request
            HttpRequest trackRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://spotify23.p.rapidapi.com/tracks/?ids=4WNcduiCmDNfmTEz7JvmLv"))
                    .header("X-RapidAPI-Key", APIKEY)
                    .header("X-RapidAPI-Host", APIHOST)
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> trackResponse = HttpClient.newHttpClient().send(trackRequest, HttpResponse.BodyHandlers.ofString());
            //System.out.println(trackResponse.body());
            //end of track request

            //Mapper for getting related artist using the artistJsonNode var
//            ObjectMapper mapper = new ObjectMapper();
//            try {
//                JsonNode artistJsonNode = mapper.readTree(res.body());
//                for (JsonNode jsonNode : artistJsonNode) {
//                    for (JsonNode artist : jsonNode) {
//
//                        String name = artist.get("name").toString();
//                        System.out.println(name);
//                    }
//                }
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            //need the make the same mapper part but more complicated for getting the `uri` (these are the
            // actual artists name) string from the json file
        }




}
