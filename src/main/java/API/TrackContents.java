package API;

public class TrackContents {
    String artistName;
    String trackName;
    int year;

    public TrackContents() {
        this.artistName = "";
        this.trackName = "";
        this.year = 0;
    }

    public String getArtistName() {
        return artistName;
    }

    public int getYear() {
        return year;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setArtistName(String name) {
        this.artistName = name;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String toStringTrack() {
        return String.format("Track Name: %-30s Artist Name: %d", this.trackName, this.artistName);

    }


}
