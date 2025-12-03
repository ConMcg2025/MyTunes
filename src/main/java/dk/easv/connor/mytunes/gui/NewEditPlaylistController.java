package dk.easv.connor.mytunes.gui;

import dk.easv.connor.mytunes.be.Playlist;
import dk.easv.connor.mytunes.bll.Logic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewEditPlaylistController {
    @FXML
    private TextField playlistNameText;

    @FXML
    private Button newEditPlaylistCancelButton;

    @FXML
    private Button newEditPlaylistSaveButton;

    private Logic logic;
    private Playlist playlistToEdit;
    private HelloController parentController;

    public void initialize() {
        logic = new Logic();
    }

    // Set the playlist to edit (for edit mode)
    public void setPlaylist(Playlist playlist) {
        this.playlistToEdit = playlist;
        if (playlist != null) {
            playlistNameText.setText(playlist.getName());
        }
    }

    // Set parent controller for callback
    public void setParentController(HelloController controller) {
        this.parentController = controller;
    }

    public void onNewEditPlaylistCancelButtonClick(ActionEvent actionEvent) {
        closeWindow();
    }

    public void onNewEditPlaylistSaveButtonClick(ActionEvent actionEvent) {
        // Validate input
        if (playlistNameText.getText().isEmpty()) {
            showError("Playlist name is required!");
            return;
        }

        try {
            if (playlistToEdit == null) {
                // Create new playlist
                Playlist newPlaylist = new Playlist(playlistNameText.getText());
                logic.createPlaylist(newPlaylist);
                showSuccess("Playlist created successfully!");
            } else {
                // Update existing playlist
                playlistToEdit.setName(playlistNameText.getText());
                logic.updatePlaylist(playlistToEdit);
                showSuccess("Playlist updated successfully!");
            }

            // Refresh parent controller's playlist list
            if (parentController != null) {
                parentController.loadPlaylistsAfterUpdate();
            }

            closeWindow();

        } catch (Exception e) {
            showError("Error saving playlist: " + e.getMessage());
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
        Stage stage = (Stage) newEditPlaylistCancelButton.getScene().getWindow();
        stage.close();
    }
}