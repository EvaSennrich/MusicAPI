package API;

public class AlbumContents {
    String name;
    int year;

    public void AlbumContents() {
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
        return String.format("Album Name: %-50s Released in: %d", this.name, this.year);

    }


}
//"Type->Fruit-> Type: %-10s \t\t Weight: %.1f\n", getType(), getWeight());