package dk.easv.connor.mytunes.gui;

import dk.easv.connor.mytunes.be.Song;
import dk.easv.connor.mytunes.bll.Logic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class NewEditSongController {
    @FXML
    private TextField songTitleText;

    @FXML
    private TextField songArtistText;

    @FXML
    private TextField songCategoryText;

    @FXML
    private TextField songTimeText;

    @FXML
    private TextField songFileText;

    @FXML
    private Button chooseFileButton;

    @FXML
    private Button newEditSongCancelButton;

    @FXML
    private Button newEditSongSaveButton;

    private Logic logic;
    private Song songToEdit;
    private HelloController parentController;

    public void initialize() {
        logic = new Logic();
    }

    // Set the song to edit (for edit mode)
    public void setSong(Song song) {
        this.songToEdit = song;
        if (song != null) {
            songTitleText.setText(song.getTitle());
            songArtistText.setText(song.getArtist());
            songCategoryText.setText(song.getGenre());
            songTimeText.setText(String.valueOf(song.getDuration()));
            songFileText.setText(song.getFilePath());
        }
    }

    // Set parent controller for callback
    public void setParentController(HelloController controller) {
        this.parentController = controller;
    }

    public void onMoreSongCategoryButtonClick(ActionEvent actionEvent) {
        // Optional: Open a dialog to add more categories
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Categories");
        alert.setHeaderText("Available Categories");
        alert.setContentText("Pop, Rock, Rap, Jazz, Classical, Electronic, Country, Hip-Hop");
        alert.showAndWait();
    }

    public void onChooseFileButtonClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Music File");

        // Set file extension filters
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav", "*.m4a", "*.flac"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        Stage stage = (Stage) chooseFileButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            songFileText.setText(selectedFile.getAbsolutePath());
        }
    }

    public void onNewEditSongCancelButtonClick(ActionEvent actionEvent) {
        closeWindow();
    }

    public void onNewEditSongSaveButtonClick(ActionEvent actionEvent) {
        // Validate input
        if (songTitleText.getText().isEmpty()) {
            showError("Title is required!");
            return;
        }

        if (songFileText.getText().isEmpty()) {
            showError("Please select a music file!");
            return;
        }

        try {
            // Parse duration (expecting seconds as integer)
            int duration = 0;
            if (!songTimeText.getText().isEmpty()) {
                duration = Integer.parseInt(songTimeText.getText());
            }

            if (songToEdit == null) {
                // Create new song
                Song newSong = new Song(
                        songTitleText.getText(),
                        songArtistText.getText(),
                        songCategoryText.getText(),
                        duration,
                        songFileText.getText()
                );
                logic.createSong(newSong);
                showSuccess("Song created successfully!");
            } else {
                // Update existing song
                songToEdit.setTitle(songTitleText.getText());
                songToEdit.setArtist(songArtistText.getText());
                songToEdit.setGenre(songCategoryText.getText());
                songToEdit.setDuration(duration);
                songToEdit.setFilePath(songFileText.getText());
                logic.updateSong(songToEdit);
                showSuccess("Song updated successfully!");
            }

            // Refresh parent controller's song list
            if (parentController != null) {
                parentController.loadSongs();
            }

            closeWindow();

        } catch (NumberFormatException e) {
            showError("Duration must be a valid number (in seconds)!");
        } catch (Exception e) {
            showError("Error saving song: " + e.getMessage());
            e.printStackTrace();
        }
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

    private void closeWindow() {
        Stage stage = (Stage) newEditSongCancelButton.getScene().getWindow();
        stage.close();
    }
}