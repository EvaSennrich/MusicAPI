import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import API.*;
import org.json.JSONObject;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        String artistId;
        ArtistMap hashmap = new ArtistMap();
        Scanner scanner = new Scanner(System.in); //user's input


        while (true) {
            System.out.println("Enter your choice:");
            System.out.println("1. Search for a song name");
            System.out.println("2. Search for an artist name");
            System.out.println("3. Exit");

            int choice = 0;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear the input buffer
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid choice.");
                scanner.nextLine(); // Clear the input buffer
                continue;
            }

            if (choice == 1) {
                System.out.print("Enter song name: ");
                String songName = scanner.nextLine();
                // Perform search for song name
                String trackResponse = GetTrack.getTrack(songName);
                List<String> tracks = GetTrack.extractTrackDataFromResponse(trackResponse);

            } else if (choice == 2) {
                System.out.print("Enter artist name: ");
                String artistName = scanner.nextLine();

                try {

                String searchArtistRes = SearchArtist.searchArtist(artistName); //search artist's info based on user's input from scanner
                JSONObject result = new JSONObject(searchArtistRes);//new jsonObject to parse artist's ID

                //extracts the artist ID
                String id = result.getJSONObject("artists").getJSONArray("items").getJSONObject(0).getJSONObject("data").getString("uri");
                //splits json uri to get artist's ID
                String[] tokens = id.split(":");
                artistId = tokens[tokens.length - 1];
//            System.out.println(artistId);

                //checks if the artist ID it's already on the hashmap
                if (hashmap.getHashMapList().containsKey(artistId)) {
                    System.out.println("Extracting info from HashMap");
//                    System.out.println("Album Name: WHATEVER");
                    System.out.println(hashmap.getHashMapList().toString());
                } else { //else retrieves the artist's info from the API

                    //and then store it in the hashmap
                    hashmap.setHashMap(artistId, new HashMap<>());


                    //getting artist bio
                    String bioResponse = GetBio.getBio(artistId);
                    List<String> bio = GetBio.extractBiosFromResponse(bioResponse);

                    //get genres response
                    String genreResponse = GetGenres.getGenres(artistId);
                    //extracts genres from the artist search/getArtist
                    List<String> genres = GetGenres.extractGenresFromResponse(genreResponse);

                    //get top music response
                    String topMscRes = TopMusic.getTopMusic(artistId);
                    //extracts top music from response
                    List<String> topMusics = TopMusic.extractTopMusicFromResponse(topMscRes);

                    //extracts related artist  from the artist search/getArtist
                    List<String> relatedArtist = RelatedArtists.getRelatedArtists(artistId);

                    //extract albums from the artist search/getArtist
                    List<AlbumContents> albums = SearchArtist.extractAlbumsFromResponse(searchArtistRes);
                    //sorts albums from newest to oldest released
                    Collections.sort(albums, (o1, o2) -> o2.getYear() - o1.getYear());

                    //Loops through the arraylists to print each album, genres, related artists.
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
                    }//end of for each


                    System.out.println("===============================================================================================================================================================================================================================================================================================");
                    System.out.println("                                                           Album Content ");
                    System.out.println("===============================================================================================================================================================================================================================================================================================");

                    for (AlbumContents album : albums) {
                        System.out.println(album.toString());
                    }


                    hashmap.getHashMap(artistId).put("albumContents", albums);
                    System.out.println("____________________________________________________________________________________________________________________________________________________________________________________________________________________");
                    System.out.println("Storing data in HashMap " );
                    System.out.println("____________________________________________________________________________________________________________________________________________________________________________________________________________________");
                    // Call the printHashMapAsJson() function to print the hashmap as JSON
                    hashmap.printHashMapAsJson(artistId);
                    System.out.println("____________________________________________________________________________________________________________________________________________________________________________________________________________________");
                    System.out.println("Data Successfully Stored in HashMap! ");
                    System.out.println("____________________________________________________________________________________________________________________________________________________________________________________________________________________");

                }//end of else

            } catch(IOException | InterruptedException e){
                e.printStackTrace();
            }

            } else if (choice == 3) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid choice.");
            }
        }

        scanner.close();
    }
}
