import API.AlbumContents;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;

public class ArtistMap {

    HashMap<String, HashMap> hashMapList;

    public ArtistMap() {
        this.hashMapList = new HashMap<>() {
        };
    }

    public HashMap<String, HashMap> getHashMapList() {
        return hashMapList;
    }

    public HashMap getHashMap(String key) {
        return hashMapList.get(key);
    }

    public void setHashMap(String key, HashMap value) {
        this.hashMapList.put(key, value);
    }


    // Function to print the hashmap formatted as JSON
    public void printHashMapAsJson(String artistId) {
        HashMap<String, Object> artistInfo = getHashMap(artistId);
        if (artistInfo != null) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(artistInfo);
            System.out.println(json);
        } else {
            System.out.println("Artist info not found in the hashmap.");
        }
    }


}//end of ArtistMap class
