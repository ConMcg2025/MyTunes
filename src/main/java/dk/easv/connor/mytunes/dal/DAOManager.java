package dk.easv.connor.mytunes.dal;

public class DAOManager {

    private final SongDAO songDAO;
    private final PlaylistDAO playlistDAO;

    public DAOManager() {
        songDAO = new SongDAO();
        playlistDAO = new PlaylistDAO();
    }

    public SongDAO getSongDAO() {
        return songDAO;
    }

    public PlaylistDAO getPlaylistDAO() {
        return playlistDAO;
    }
}