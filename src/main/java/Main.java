<<<<<<< HEAD
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import API.AlbumContents;
import org.json.JSONObject;

import API.GetGenres;
import API.SearchArtist;
=======
import API.AlbumContents;
import API.GetArtist;
import org.json.*;
import org.json.JSONObject;
>>>>>>> 11da5f553e066bf629350ae4a26f8a8b912058fb

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
<<<<<<< HEAD
        ArtistMap hashmap = new ArtistMap();
        Scanner scanner = new Scanner(System.in); //user's input

        while (true) {

            System.out.print("Enter artist name: ");
            String artistName = scanner.nextLine();


            try {
                //search artist's info based on user's input from scanner
                String res = SearchArtist.searchArtist(artistName);

                //new jsonObject to parse artist's ID
                JSONObject result = new JSONObject(res);
                String id = result.getJSONObject("artists").getJSONArray("items").getJSONObject(0).getJSONObject("data").getString("uri");
                //splits json uri to get artist's ID
                String[] tokens = id.split(":");
                artistId = tokens[tokens.length - 1];
=======

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
>>>>>>> 11da5f553e066bf629350ae4a26f8a8b912058fb
//            System.out.println(artistId);

                //checks if the artist ID it's already on the hashmap
                if (hashmap.getHashMapList().containsKey(artistId)) {
                    System.out.println("Already in the HashMap");
//                    System.out.println("Album Name: WHATEVER");
                    System.out.println(hashmap.getHashMapList().toString());
                } else {

                    hashmap.setHashMap(artistId, new HashMap<>());

<<<<<<< HEAD
=======
            //extract albums from the artist search/getArtist
            List<AlbumContents> albums = GetArtist.extractAlbumsFromResponse(res);
            //sorts albums from newest to oldest released
            Collections.sort(albums, (o1, o2) -> o2.getYear() - o1.getYear());
            for (AlbumContents album : albums) {
                System.out.println(album.toString());
            }
>>>>>>> 11da5f553e066bf629350ae4a26f8a8b912058fb

                    String genreResponse = API.GetGenres.getGenres(artistId);

                    //extracts albums from the artist search/getArtist
                    List<String> genres = GetGenres.extractGenresFromResponse(genreResponse);

                    //extracts genres from the artist search/getArtist
                    List<String> relatedArtist = API.RelatedArtists.getRelatedArtists(artistId);

                    //extract albums from the artist search/getArtist
                    List<AlbumContents> albums = SearchArtist.extractAlbumsFromResponse(res);

                    //sorts albums from newest to oldest released
                    Collections.sort(albums, (o1, o2) -> o2.getYear() - o1.getYear());

                    for (AlbumContents album : albums) {
                        for (String genre : genres) {
                            album.setGenres(genre);
                        }
                            for (String relArtists : relatedArtist) {
                                album.setRelatedArtist(relArtists);
                            }
                    }

                    hashmap.getHashMap(artistId).put("albumContents", albums);
                    System.out.println("Storing data in HashMap " + hashmap.getHashMap(artistId).toString());

                    for (AlbumContents album : albums) {
                        System.out.println(album.toString());
                    }
                }

            } catch(IOException | InterruptedException e){
                e.printStackTrace();
            }//end of try catch
        }

    }//end of main


} //end of Main class
