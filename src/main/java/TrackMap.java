import java.util.HashMap;

public class TrackMap {

    HashMap<String, HashMap> hashMapList;

    public TrackMap() {
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

