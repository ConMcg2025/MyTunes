package dk.easv.connor.mytunes.dal;

import dk.easv.connor.mytunes.be.Song;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongDAO {

    private final ConnectionManager dbConnector;

    public SongDAO() {
        dbConnector = new ConnectionManager();
    }

    public List<Song> getAllSongs() throws Exception {
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT * FROM Songs";

        try (Connection conn = dbConnector.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Song song = new Song(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("artist"),
                        rs.getString("genre"),
                        rs.getInt("duration"),
                        rs.getString("filePath")
                );
                songs.add(song);
            }
        }
        return songs;


    }

    public Song createSong(Song song) throws Exception {
        String sql = "INSERT INTO Songs (title, artist, genre, duration, filePath) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dbConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, song.getTitle());
            pstmt.setString(2, song.getArtist());
            pstmt.setString(3, song.getGenre());
            pstmt.setInt(4, song.getDuration());
            pstmt.setString(5, song.getFilePath());

            pstmt.executeUpdate();

            // Get the generated ID
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                song.setId(rs.getInt(1));
            }
        }
        return song;
    }

    public void updateSong(Song song) throws Exception {
        String sql = "UPDATE Songs SET title = ?, artist = ?, genre = ?, duration = ?, filePath = ? WHERE id = ?";

        try (Connection conn = dbConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, song.getTitle());
            pstmt.setString(2, song.getArtist());
            pstmt.setString(3, song.getGenre());
            pstmt.setInt(4, song.getDuration());
            pstmt.setString(5, song.getFilePath());
            pstmt.setInt(6, song.getId());

            pstmt.executeUpdate();
        }
    }

    public void deleteSong(Song song) throws Exception {
        String sql = "DELETE FROM Songs WHERE id = ?";

        try (Connection conn = dbConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, song.getId());
            pstmt.executeUpdate();
        }
    }

    public List<Song> searchSongs(String query) throws Exception {
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT * FROM Songs WHERE title LIKE ? OR artist LIKE ? OR genre LIKE ?";

        try (Connection conn = dbConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + query + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Song song = new Song(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("artist"),
                        rs.getString("genre"),
                        rs.getInt("duration"),
                        rs.getString("filePath")
                );
                songs.add(song);
            }
        }
        return songs;
    }
}