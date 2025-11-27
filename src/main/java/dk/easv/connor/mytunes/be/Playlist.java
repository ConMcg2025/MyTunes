package dk.easv.connor.mytunes.be;

public class Playlist {
    private int id;
    private String name;
    private int totalDuration; // in seconds

    public Playlist(int id, String name, int totalDuration) {
        this.id = id;
        this.name = name;
        this.totalDuration = totalDuration;
    }

    // Constructor without ID (for creating new playlists)
    public Playlist(String name) {
        this.name = name;
        this.totalDuration = 0;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }

    @Override
    public String toString() {
        return name;
    }
}