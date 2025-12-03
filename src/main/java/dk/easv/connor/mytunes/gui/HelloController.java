package dk.easv.connor.mytunes.gui;

import dk.easv.connor.mytunes.be.Playlist;
import dk.easv.connor.mytunes.be.Song;
import dk.easv.connor.mytunes.bll.Logic;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Button reverseSongButton;

    @FXML
    private Button playSongButton;

    @FXML
    private Button skipSongButton;

    @FXML
    private TextField filterSongText;

    @FXML
    private Button searchFilterButton;

    @FXML
    private Slider volumeSlider;

    @FXML
    private ListView<Playlist> playlistList;

    @FXML
    private ListView<Song> songPlaylistList;

    @FXML
    private Button songToPlaylistButton;

    @FXML
    private ListView<Song> songList;

    @FXML
    private Button newPlaylistButton;

    @FXML
    private Button editPlaylistButton;

    @FXML
    private Button deletePlaylistButton;

    @FXML
    private Button upSongPlaylistButton;

    @FXML
    private Button downSongPlaylistButton;

    @FXML
    private Button deleteSongPlaylistButton;

    @FXML
    private Button newSongButton;

    @FXML
    private Button editSongButton;

    @FXML
    private Button deleteSongButton;

    @FXML
    private Button closeApplicationButton;

    private Logic logic;
    private ObservableList<Song> songs;
    private ObservableList<Playlist> playlists;
    private ObservableList<Song> songsInPlaylist;

    @FXML
    public void initialize() {
        logic = new Logic();
        songs = FXCollections.observableArrayList();
        playlists = FXCollections.observableArrayList();
        songsInPlaylist = FXCollections.observableArrayList();

        songList.setItems(songs);
        playlistList.setItems(playlists);
        songPlaylistList.setItems(songsInPlaylist);

        // Load data
        loadSongs();
        loadPlaylists();
    }

    public void loadSongs() {
        try {
            songs.clear();
            songs.addAll(logic.getAllSongs());
        } catch (Exception e) {
            showError("Error loading songs: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadPlaylists() {
        try {
            playlists.clear();
            playlists.addAll(logic.getAllPlaylists());
        } catch (Exception e) {
            showError("Error loading playlists: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void onReverseSongButtonClick(ActionEvent actionEvent) {
        // TODO: Implement previous song functionality
        showInfo("Previous song - Not yet implemented");
    }

    public void onPlaySongButtonClick(ActionEvent actionEvent) {
        // TODO: Implement play/pause functionality
        showInfo("Play/Pause - Not yet implemented");
    }

    public void onSkipSongButtonClick(ActionEvent actionEvent) {
        // TODO: Implement next song functionality
        showInfo("Next song - Not yet implemented");
    }

    public void onSearchFilterButtonClick(ActionEvent actionEvent) {
        String query = filterSongText.getText().trim();
        if (query.isEmpty()) {
            loadSongs();
            return;
        }

        try {
            songs.clear();
            songs.addAll(logic.filterSongs(query));
        } catch (Exception e) {
            showError("Error filtering songs: " + e.getMessage());
        }
    }

    public void onSongToPlaylistButtonClick(ActionEvent actionEvent) {
        Song selectedSong = songList.getSelectionModel().getSelectedItem();
        Playlist selectedPlaylist = playlistList.getSelectionModel().getSelectedItem();

        if (selectedSong == null) {
            showError("Please select a song!");
            return;
        }

        if (selectedPlaylist == null) {
            showError("Please select a playlist!");
            return;
        }

        try {
            logic.addSongToPlaylist(selectedPlaylist, selectedSong);
            loadSongsInPlaylist(selectedPlaylist);
            showSuccess("Song added to playlist!");
        } catch (Exception e) {
            showError("Error adding song to playlist: " + e.getMessage());
        }
    }

    public void onNewPlaylistButtonClick(ActionEvent actionEvent) {
        openNewEditPlaylistWindow(null);
    }

    public void onEditPlaylistButtonClick(ActionEvent actionEvent) {
        Playlist selected = playlistList.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Please select a playlist to edit!");
            return;
        }
        openNewEditPlaylistWindow(selected);
    }

    public void onDeletePlaylistButtonClick(ActionEvent actionEvent) {
        Playlist selected = playlistList.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Please select a playlist to delete!");
            return;
        }

        if (showConfirmation("Are you sure you want to delete this playlist?")) {
            try {
                logic.deletePlaylist(selected);
                loadPlaylists();
                songsInPlaylist.clear();
                showSuccess("Playlist deleted!");
            } catch (Exception e) {
                showError("Error deleting playlist: " + e.getMessage());
            }
        }
    }

    public void onUpSongPlaylistButtonClick(ActionEvent actionEvent) {
        // TODO: Implement move song up in playlist
        showInfo("Move song up - Not yet implemented");
    }

    public void onDownSongPlaylistButtonClick(ActionEvent actionEvent) {
        // TODO: Implement move song down in playlist
        showInfo("Move song down - Not yet implemented");
    }

    public void onDeleteSongPlaylistButtonClick(ActionEvent actionEvent) {
        Song selectedSong = songPlaylistList.getSelectionModel().getSelectedItem();
        Playlist selectedPlaylist = playlistList.getSelectionModel().getSelectedItem();

        if (selectedSong == null) {
            showError("Please select a song to remove!");
            return;
        }

        if (selectedPlaylist == null) {
            showError("Please select a playlist!");
            return;
        }

        try {
            logic.removeSongFromPlaylist(selectedPlaylist, selectedSong);
            loadSongsInPlaylist(selectedPlaylist);
            showSuccess("Song removed from playlist!");
        } catch (Exception e) {
            showError("Error removing song: " + e.getMessage());
        }
    }

    public void onNewSongButtonClick(ActionEvent actionEvent) {
        openNewEditSongWindow(null);
    }

    public void onEditSongButtonClick(ActionEvent actionEvent) {
        Song selected = songList.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Please select a song to edit!");
            return;
        }
        openNewEditSongWindow(selected);
    }

    public void onDeleteSongButtonClick(ActionEvent actionEvent) {
        Song selected = songList.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Please select a song to delete!");
            return;
        }

        if (showConfirmation("Are you sure you want to delete this song?")) {
            try {
                logic.deleteSong(selected);
                loadSongs();
                showSuccess("Song deleted!");
            } catch (Exception e) {
                showError("Error deleting song: " + e.getMessage());
            }
        }
    }

    public void onCloseApplicationButtonClick(ActionEvent actionEvent) {
        Platform.exit();
    }

    // Helper method to open New/Edit Song window
    private void openNewEditSongWindow(Song song) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/connor/mytunes/new-edit-song.fxml"));
            Scene scene = new Scene(loader.load());

            NewEditSongController controller = loader.getController();
            controller.setParentController(this);
            if (song != null) {
                controller.setSong(song);
            }

            Stage stage = new Stage();
            stage.setTitle(song == null ? "New Song" : "Edit Song");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            showError("Error opening song window: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper method to open New/Edit Playlist window
    private void openNewEditPlaylistWindow(Playlist playlist) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/connor/mytunes/new-edit-playlist.fxml"));
            Scene scene = new Scene(loader.load());

            NewEditPlaylistController controller = loader.getController();
            controller.setParentController(this);
            if (playlist != null) {
                controller.setPlaylist(playlist);
            }

            Stage stage = new Stage();
            stage.setTitle(playlist == null ? "New Playlist" : "Edit Playlist");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            showError("Error opening playlist window: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadSongsInPlaylist(Playlist playlist) {
        try {
            songsInPlaylist.clear();
            songsInPlaylist.addAll(logic.getSongsInPlaylist(playlist));
        } catch (Exception e) {
            showError("Error loading songs in playlist: " + e.getMessage());
        }
    }

    public void loadPlaylistsAfterUpdate() {
        loadPlaylists();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }
}