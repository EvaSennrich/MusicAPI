package API;

public class TrackContents {
    String artistName;
    String trackName;
    String trackAlbum;

    public TrackContents() {
        this.artistName = "";
        this.trackName = "";
        this.trackAlbum = "";
    }

    public String getArtistName() {
        return artistName;
    }

    public String getTrackAlbum() {
        return trackAlbum;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setArtistName(String name) {
        this.artistName = name;
    }

    public void setTrackAlbum(String trackAlbum) {
        this.trackAlbum = trackAlbum;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String toString() {
        return String.format("Track Name: %-30s Artist Name: %-30s Album Name: %-30s", this.trackName, this.artistName, this.trackAlbum);

    }


}
