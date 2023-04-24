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
        String trackId;
        ArtistMap hashmap = new ArtistMap();
        TrackMap trackmap = new TrackMap();
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
                String trackName = scanner.nextLine();
                // Perform search for song name


                String trackResponse = API.GetTrack.getTrack(trackName); //search artist's info based on user's input from scanner
                JSONObject result = new JSONObject(trackResponse);//new jsonObject to parse artist's ID

                //extracts the track ID
                String id = result.getJSONObject("tracks").getJSONArray("items").getJSONObject(0).getJSONObject("data").getString("uri");
                //splits json uri to get track's ID
                String[] tokens = id.split(":");
                trackId = tokens[tokens.length - 1];

                System.out.println("Track ID: " + trackId);
                if(trackmap.getHashMapList().containsKey(trackId)){
                    System.out.println("");
                    System.out.println("Extracting info from TrackMap");
                    System.out.println("==============================" + "Track Info" + "=================================");
                    System.out.println();
                    System.out.println(trackmap.getHashMap(trackId).get("trackContents").toString());
                    System.out.println();
                    System.out.println("==================================================================================");

                }
                else{

                    System.out.println("____________________________________________________________________________________________________________________________________________________________________________________________________________________");
                    System.out.println("Storing data in TrackMap");
                    System.out.println();
                    trackmap.setHashMap(trackId, new HashMap<>());
                    List<TrackContents> tracks = GetTrack.extractTrackDataFromResponse(trackResponse);
                    trackmap.getHashMap(trackId).put("trackContents", tracks.toString());
                    System.out.println("==============================" + "Track Info" + "=================================");
                    System.out.println();
                    System.out.println(tracks.toString());
                    System.out.println();
                    System.out.println("==================================================================================");

                }







            } else if (choice == 2) {
                System.out.print("Enter artist name: ");
                String artistName = scanner.nextLine();
                // Perform search for artist name
//                String searchArtistRes = SearchArtist.searchArtist(artistName); //search artist's info based on user's input from scanner
//                JSONObject result = new JSONObject(searchArtistRes);//new jsonObject to parse artist's ID
//
//                //extracts the artist ID
//                String id = result.getJSONObject("artists").getJSONArray("items").getJSONObject(0).getJSONObject("data").getString("uri");
//                //splits json uri to get artist's ID
//                String[] tokens = id.split(":");
//                artistId = tokens[tokens.length - 1];
//
//                //get genres response
//                String genreResponse = API.GetGenres.getGenres(artistId);
//                //extracts genres from the artist search/getArtist
//                List<String> genres = GetGenres.extractGenresFromResponse(genreResponse);
//
//                //get top music response
//                String topMscRes = API.TopMusic.getTopMusic(artistId);
//                //extracts top music from response
//                List<String> topMusics = API.TopMusic.extractTopMusicFromResponse(topMscRes);
//
//                //extracts related artist  from the artist search/getArtist
//                List<String> relatedArtist = API.RelatedArtists.getRelatedArtists(artistId);
//
//                //extract albums from the artist search/getArtist
//                List<AlbumContents> albums = SearchArtist.extractAlbumsFromResponse(searchArtistRes);
//                //sorts albums from newest to oldest released
//                Collections.sort(albums, (o1, o2) -> o2.getYear() - o1.getYear());
//
//                //Loops through the arraylists to print each album, genres, related artists.
//                for (AlbumContents album : albums) {
//                    for (String genre : genres) {
//                            album.setGenres(genre);
//                    }
//                        for (String relArtists : relatedArtist) {
//                            album.setRelatedArtist(relArtists);
//                        }
//                            for (String top : topMusics) {
//                            album.setTopMusic(top);
//                            }
//                }//end of for each

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
                    System.out.println("");
                    System.out.println("Extracting info from HashMap");
                    GetBio.printFormattedBio(hashmap.getHashMap(artistId).get("bioContents").toString(), 100);

                    List<AlbumContents> albums = (List<AlbumContents>) hashmap.getHashMap(artistId).get("albumContents");
                    for (AlbumContents album : albums) {
                        System.out.println("=============================================================================================================================================================================================================");
                        System.out.println(album.toString());
                        System.out.println("=============================================================================================================================================================================================================");

                    }


                } else { //else retrieves the artist's info from the API

                    //and then store it in the hashmap
                    hashmap.setHashMap(artistId, new HashMap<>());


                    //getting artist bio
                    String bioResponse = API.GetBio.getBio(artistId);
                    List<String> bio = GetBio.extractBiosFromResponse(bioResponse);

                    GetBio.printFormattedBio(bio.get(0), 100);

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


                    hashmap.getHashMap(artistId).put("albumContents", albums);
                    hashmap.getHashMap(artistId).put("bioContents", bio.get(0));
                    System.out.println("____________________________________________________________________________________________________________________________________________________________________________________________________________________");
                    //System.out.println("Storing data in HashMap " + hashmap.getHashMap(artistId).toString());
                    System.out.println("Storing data in HashMap");
                    System.out.println();


                    for (AlbumContents album : albums) {
                        System.out.println("=============================================================================================================================================================================================================");
                        System.out.println(album.toString());
                        System.out.println("=============================================================================================================================================================================================================");

                    }

                }

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
