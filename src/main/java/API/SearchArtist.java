package API;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


public class SearchArtist {


    public static String searchArtist(String artistName) throws IOException, InterruptedException {
        //environmental vars for API keys
        Dotenv dotenv = Dotenv.load();
        String APIKEY = dotenv.get("APIKEY");
        String APIHOST = dotenv.get("APIHOST");

        //to get artists with more than one name
        String encodedArtistName = URLEncoder.encode(artistName, StandardCharsets.UTF_8);

        //interpolating "artistName" input into URI
        URI uri = URI.create("https://spotify23.p.rapidapi.com/search/?q=" + encodedArtistName + "&type=artist&offset=0");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("X-RapidAPI-Key", APIKEY)
                .header("X-RapidAPI-Host", APIHOST)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

//        System.out.println(response.body());
        return response.body();

    }//end of constructor getArtist()


    public static List<AlbumContents> extractAlbumsFromResponse(String response) {
        List<AlbumContents> result = new ArrayList<AlbumContents>();
        JSONObject obj = new JSONObject(response);
        JSONArray albums = obj.getJSONObject("albums").getJSONArray("items");

        for (int i = 0; i < albums.length(); i++) {
            AlbumContents contents = new AlbumContents();
            int year = albums.getJSONObject(i).getJSONObject("data").getJSONObject("date").getInt("year");
            String albumName = albums.getJSONObject(i).getJSONObject("data").getString("name");
            contents.setName(albumName);
            contents.setYear(year);
            result.add(contents);
        }

        return result;

    }//end of extractAlbumsFromResponse()


}// end of class GetArtist()
