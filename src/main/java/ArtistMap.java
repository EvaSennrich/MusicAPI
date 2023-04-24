import java.util.ArrayList;
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



}
