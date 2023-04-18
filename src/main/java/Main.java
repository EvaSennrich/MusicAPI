import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

<<<<<<< HEAD
import API.AlbumContents;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONObject;
=======
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
>>>>>>> 2cb5483038e9e4067e8ff96b4526ae6ea0ddc040

import java.util.*;


public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        //RELATED ARTISTS --> need the change the fact is returning results from one specific artist
//            List<String> relatedArtists = RelatedArtists.getRelatedArtists();
//            HashMap map = new HashMap<>();
//            map.put("Related Artists", relatedArtists);
//            System.out.println(map);
//======================================================================================================================

        String artistId;
        //user's input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter artist name: ");
        String artistName = scanner.nextLine();
        scanner.close();

        try {
            //search artist's info based on user's input from scanner
            String res = API.GetArtist.getArtist(artistName);
            //new jsonObject to parse artist's ID
            JSONObject result = new JSONObject(res);
            String id = result.getJSONObject("artists").getJSONArray("items").getJSONObject(0).getJSONObject("data").getString("uri");
            //splits json uri to get artist's ID
            String[] tokens = id.split(":");
            artistId = tokens[tokens.length - 1];
//            System.out.println(artistId);

//            API.GetGenres.getGenres(artistId);
//            API.GetGenres.getGenres("3TVXtAsR1Inumwj472S9r4");
//            API.GetGenres.getGenres("0JQ5DAqbMKFEC4WFtoNRpw");

            //extract albums from the artist search/getArtist
            List<AlbumContents> albums = API.GetArtist.extractAlbumsFromResponse(res);
            //sorts albums from newest to oldest released
            Collections.sort(albums, (o1, o2) -> o2.getYear() - o1.getYear());
            for (AlbumContents album : albums) {
                System.out.println(album.toString());
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }//end of try catch


    }//end of main


} //end of Main class
