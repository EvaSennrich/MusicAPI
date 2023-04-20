package API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

public class GetTrack {

    public static String getTrack(String trackName) throws IOException, InterruptedException {

        //environmental vars for API keys
        Dotenv dotenv = Dotenv.load();
        String APIKEY = dotenv.get("APIKEY");
        String APIHOST = dotenv.get("APIHOST");

        java.util.List<String> Tracks = new ArrayList<String>();

        URI uri = URI.create("https://spotify23.p.rapidapi.com/artist_related/?id=" + trackName);

        HttpRequest trackRequest = HttpRequest.newBuilder()
                .uri(uri)
                .header("X-RapidAPI-Key", APIKEY)
                .header("X-RapidAPI-Host", APIHOST)
                .header("accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(trackRequest, HttpResponse.BodyHandlers.ofString());

//        System.out.println(response.body());
        return response.body();

    }
    public static List<TrackContents> extractTrackDataFromResponse(String response) {
        List<TrackContents> result = new ArrayList<TrackContents>();
        JSONObject obj = new JSONObject(response);
        JSONArray track = obj.getJSONObject("track").getJSONArray("items");


            TrackContents contents = new TrackContents();
            int year = track.getJSONObject(0).getJSONObject("data").getJSONObject("date").getInt("year");
            String trackName = track.getJSONObject(0).getJSONObject("data").getString("name");
            contents.setName(trackName);
            contents.setYear(year);
            result.add(contents);


        return result;

    }//end of extractAlbumsFromResponse()


}// end of class GetArtist()




