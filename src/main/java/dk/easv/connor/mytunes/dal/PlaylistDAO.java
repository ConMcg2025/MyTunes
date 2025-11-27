package dk.easv.connor.mytunes.dal;

import dk.easv.connor.mytunes.be.Playlist;
import dk.easv.connor.mytunes.be.Song;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {

    private final ConnectionManager dbConnector;

    public PlaylistDAO() {
        dbConnector = new ConnectionManager();
    }

    public List<Playlist> getAllPlaylists() throws Exception {
        List<Playlist> playlists = new ArrayList<>();
        String sql = "SELECT * FROM Playlists";

        try (Connection conn = dbConnector.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Playlist playlist = new Playlist(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("totalDuration")
                );
                playlists.add(playlist);
            }
        }
        return playlists;
    }

    public Playlist createPlaylist(Playlist playlist) throws Exception {
        String sql = "INSERT INTO Playlists (name, totalDuration) VALUES (?, ?)";

        try (Connection conn = dbConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, playlist.getName());
            pstmt.setInt(2, playlist.getTotalDuration());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                playlist.setId(rs.getInt(1));
            }
        }
        return playlist;
    }

    public void updatePlaylist(Playlist playlist) throws Exception {
        String sql = "UPDATE Playlists SET name = ?, totalDuration = ? WHERE id = ?";

        try (Connection conn = dbConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, playlist.getName());
            pstmt.setInt(2, playlist.getTotalDuration());
            pstmt.setInt(3, playlist.getId());

            pstmt.executeUpdate();
        }
    }

    public void deletePlaylist(Playlist playlist) throws Exception {
        String sql = "DELETE FROM Playlists WHERE id = ?";

        try (Connection conn = dbConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, playlist.getId());
            pstmt.executeUpdate();
        }
    }

    public List<Song> getSongsInPlaylist(Playlist playlist) throws Exception {
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT s.* FROM Songs s " +
                "INNER JOIN PlaylistSongs ps ON s.id = ps.songId " +
                "WHERE ps.playlistId = ? " +
                "ORDER BY ps.position";

        try (Connection conn = dbConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, playlist.getId());
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

    public void addSongToPlaylist(Playlist playlist, Song song) throws Exception {
        String sql = "INSERT INTO PlaylistSongs (playlistId, songId, position) " +
                "VALUES (?, ?, (SELECT ISNULL(MAX(position), 0) + 1 FROM PlaylistSongs WHERE playlistId = ?))";

        try (Connection conn = dbConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, playlist.getId());
            pstmt.setInt(2, song.getId());
            pstmt.setInt(3, playlist.getId());

            pstmt.executeUpdate();
        }
    }

    public void removeSongFromPlaylist(Playlist playlist, Song song) throws Exception {
        String sql = "DELETE FROM PlaylistSongs WHERE playlistId = ? AND songId = ?";

        try (Connection conn = dbConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, playlist.getId());
            pstmt.setInt(2, song.getId());

            pstmt.executeUpdate();
        }
    }

    public void moveSongUp(Playlist playlist, Song song) throws Exception {
        // Implementation for moving song up in playlist
        // This requires swapping positions in PlaylistSongs table
    }

    public void moveSongDown(Playlist playlist, Song song) throws Exception {
        // Implementation for moving song down in playlist
        // This requires swapping positions in PlaylistSongs table
    }
}