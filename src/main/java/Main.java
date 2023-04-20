import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import API.*;
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
        ArtistMap hashmap = new ArtistMap();
        Scanner scanner = new Scanner(System.in); //user's input

        while (true) {

            System.out.print("Enter artist name: ");
            String artistName = scanner.nextLine();


            try {
                //search artist's info based on user's input from scanner
                String searchArtistRes = SearchArtist.searchArtist(artistName);

                //new jsonObject to parse artist's ID
                JSONObject result = new JSONObject(searchArtistRes);
                String id = result.getJSONObject("artists").getJSONArray("items").getJSONObject(0).getJSONObject("data").getString("uri");
                //splits json uri to get artist's ID
                String[] tokens = id.split(":");
                artistId = tokens[tokens.length - 1];
//            System.out.println(artistId);

                //checks if the artist ID it's already on the hashmap
                if (hashmap.getHashMapList().containsKey(artistId)) {
                    System.out.println("Already in the HashMap");
//                    System.out.println("Album Name: WHATEVER");
                    System.out.println(hashmap.getHashMapList().toString());
                } else {

                    hashmap.setHashMap(artistId, new HashMap<>());

                    //get genres response
                    String genreResponse = API.GetGenres.getGenres(artistId);
                    //extracts genres from the artist search/getArtist
                    List<String> genres = GetGenres.extractGenresFromResponse(genreResponse);

                    //get top music response
                    String topMscRes = API.TopMusic.getTopMusic(artistId);
                    //extracts top music from response
                    List<String> topMusics = API.TopMusic.extractTopMusicFromResponse(topMscRes);


                    //extracts related artist  from the artist search/getArtist
                    List<String> relatedArtist = API.RelatedArtists.getRelatedArtists(artistId);

                    //extract albums from the artist search/getArtist
                    List<AlbumContents> albums = SearchArtist.extractAlbumsFromResponse(searchArtistRes);
                    //sorts albums from newest to oldest released
                    Collections.sort(albums, (o1, o2) -> o2.getYear() - o1.getYear());

                    for (AlbumContents album : albums) {
                        for (String genre : genres) {
                            album.setGenres(genre);
                        }
                            for (String relArtists : relatedArtist) {
                                album.setRelatedArtist(relArtists);
                            }
                                for (String top : topMusics) {
                                    album.setTopMusic(top);
                                }
                    }

                    hashmap.getHashMap(artistId).put("albumContents", albums);
                    System.out.println("Storing data in HashMap " + hashmap.getHashMap(artistId).toString());
                    System.out.println();

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
