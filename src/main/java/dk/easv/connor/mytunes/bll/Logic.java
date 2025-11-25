package dk.easv.connor.mytunes.bll;

import dk.easv.connor.mytunes.be.Playlist;
import dk.easv.connor.mytunes.be.song;
import dk.easv.connor.mytunes.dal.DAOManager;

import java.util.List;

public class Logic {

    private final DAOManager dao;

    public Logic() {
        dao = new DAOManager();

    }

    public List<Song> getAllSong()  throws Exception {
        return dao.getSongDAO().getAllSongs();
    }


    public Song createSong(Song song) throws Exception {
        return dao.getSongDAO().createSong(song);
    }


    public void updateSong(Song song) throws Exception {
        dao.getSongDAO().updateSong(song);
    }

    public void deleteSong(Song song) throws Exception {
        dao.getSongDAO().deleteSong(song);
    }


    public List<Song> filterSongs(String query) throws Exception {
        return dao.getSongDAO().searchSongs(query);
    }


    public List<Playlist> getAllPlaylists() throws Exception {
        return dao.getPlaylistDAO().getAllPlaylists();
    }


    public Playlist createPlaylist(Playlist playlist) throws Exception {
        return dao.getPlaylistDAO().createPlaylist(playlist);
    }


    public void updatePlaylist(Playlist playlist) throws Exception {
        dao.getPlaylistDAO().updatePlaylist(playlist);
    }


    public void deletePlaylist(Playlist playlist) throws Exception {
        dao.getPlaylistDAO().deletePlaylist(playlist);
    }


    public List<Song> getAllPlaylist() throws Exception {
        return dao.getPlaylistDAO().getSongsInPlaylist(playlist);
    }

    public void addSongToPlaylist(Playlist playlist, Song song) throws Exception {
        dao.getPlaylistDAO().addSongToPlaylist(playlist, song);
    }


    public void removeSongFromPlaylist(Playlist playlist, Song song) throws Exception {
        dao.getPlaylistDAO().removeSongFromPlaylist(playlist, song);
    }


    public void moveSongUp(Playlist playlist, Song song) throws Exception {
        dao.getPlaylistDAO().moveSongUp(playlist, song);
    }


    public void moveSongDown(Playlist playlist, Song song) throws Exception {
        dao.getPlaylistDAO().moveSongDown(playlist, song);
    }


        public List<Song> getSongsForPlayback(Playlist playlist) throws Exception {
            if (playlist == null) {
                return getAllSongs();
            } else {
                return getSongsInPlaylist(playlist);
            }
        }



}
