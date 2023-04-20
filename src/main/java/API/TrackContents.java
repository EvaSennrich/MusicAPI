package API;

public class TrackContents {
    String name;
    int year;

    public void TrackContents() {
        this.name = "";
        this.year = 0;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(int year) {
        this.year = year;
    }
    public String toString() {
        return String.format("Track Name: %-50s Released in: %d", this.name, this.year);

    }


}
