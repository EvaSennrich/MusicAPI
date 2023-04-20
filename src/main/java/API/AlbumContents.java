package API;

import java.util.ArrayList;
import java.util.List;

public class AlbumContents {
    String name;
    int year;
    List<String> genres;
    List<String> relatedArtist;

    public AlbumContents() {
        this.name = "";
        this.year = 0;
        this.genres = new ArrayList<>();
        this.relatedArtist = new ArrayList<>();

    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<String> getRelatedArtist() {
        return relatedArtist;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenres(String genres) {
        this.genres.add(genres);
    }

    public void setRelatedArtist(String relatedArtist) {
        this.relatedArtist.add(relatedArtist);
    }

    public void setYear(int year) {
        this.year = year;
    }
    public String toString() {
        return String.format("Album Name: %-40s Released in: %-8d \t\t Genres: %s \t\t Related Artists: %s", this.name, this.year, this.genres, this.relatedArtist);

    }


}
