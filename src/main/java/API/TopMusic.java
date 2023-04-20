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

public class TopMusic {


    public static String getTopMusic(String artistID) throws IOException, InterruptedException {
        // Environmental vars for API keys
        Dotenv dotenv = Dotenv.load();
        String APIKEY = dotenv.get("APIKEY");
        String APIHOST = dotenv.get("APIHOST");

        URI uri = URI.create("https://spotify23.p.rapidapi.com/artist_overview/?id=" + artistID);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("X-RapidAPI-Key", APIKEY)
                .header("X-RapidAPI-Host", APIHOST)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();

    }//end of getTopMusic()


    public static List<String> extractTopMusicFromResponse(String response) {
        List<String> result = new ArrayList<>();
        JSONObject obj = new JSONObject(response);
        JSONArray topMusic = obj.getJSONObject("data").getJSONObject("artist").getJSONObject("discography").getJSONObject("topTracks").getJSONArray("items");
//        System.out.println("topMusic" + topMusic);


        for (int i = 0; i < topMusic.length(); i++) {
        JSONObject topMscName = topMusic.getJSONObject(i).getJSONObject("track");
        String name = topMscName.get("name").toString();
//            System.out.println(name);

            result.add(name);
        }

        return result;
    }



}// end of TopMusic class
