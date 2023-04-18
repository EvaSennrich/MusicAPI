import API.AlbumContents;
import API.GetArtist;
import org.json.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
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
            String res = GetArtist.getArtist(artistName);
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
            List<AlbumContents> albums = GetArtist.extractAlbumsFromResponse(res);
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
