package API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GetTrack {

    public static String getTrack(String songName) throws IOException, InterruptedException {

        //environmental vars for API keys
        Dotenv dotenv = Dotenv.load();
        String APIKEY = dotenv.get("APIKEY");
        String APIHOST = dotenv.get("APIHOST");

//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("https://spotify23.p.rapidapi.com/search/?q=" + songName + "&type=tracks&offset=0&limit=10&numberOfTopResults=5"))
//                .header("X-RapidAPI-Key", APIKEY)
//                .header("X-RapidAPI-Host", APIHOST)
//                .method("GET", HttpRequest.BodyPublishers.noBody())
//                .build();
//        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());

        String encodedSongName = URLEncoder.encode(songName, StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://spotify23.p.rapidapi.com/search/?q=" + encodedSongName + "&type=multi&offset=0&limit=10&numberOfTopResults=5"))
                .header("X-RapidAPI-Key", APIKEY)
                .header("X-RapidAPI-Host", APIHOST)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println("getTrack response" + response.body());

        return response.body();

    }//end of getTrack()


    public static List<TrackContents> extractTrackDataFromResponse(String response) {
        List<TrackContents> result = new ArrayList<TrackContents>();
        JSONObject obj = new JSONObject(response);
        JSONArray tracks = obj.getJSONObject("tracks").getJSONArray("items");


        TrackContents contents = new TrackContents();

        //gets the name of the track/song
        String trackName = tracks.getJSONObject(0).getJSONObject("data").getString("name");
        //extracts artist name from track
        JSONArray trackArtistsNames = tracks.getJSONObject(0).getJSONObject("data").getJSONObject("artists").getJSONArray("items");
        String trackAlbum = tracks.getJSONObject(0).getJSONObject("data").getJSONObject("albumOfTrack").getString("name");

            //for loop that iterates through the artists object to get multiple artists names if so
            for (int j = 0; j < trackArtistsNames.length(); j++) {
                String artistsNames = trackArtistsNames.getJSONObject(j).getJSONObject("profile").getString("name");
//                System.out.println("artistsNames "+ artistsNames);
                if(contents.getArtistName() == ""){
                    contents.setArtistName(contents.getArtistName() + artistsNames);
                }
                else {
                    contents.setArtistName(contents.getArtistName() + ", " + artistsNames);
                }

            }//end of loop

            contents.setTrackName(trackName);
            contents.setTrackAlbum(trackAlbum);
            result.add(contents);

       /* System.out.println("==============================" + "Track Info" + "=================================");
        System.out.println();
        contents.toStringTrack();
        System.out.println();
        System.out.println("==================================================================================");
*/
        return result;

    }//end of extractAlbumsFromResponse()


}// end of class GetArtist()




