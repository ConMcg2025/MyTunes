package dk.easv.connor.mytunes.gui;

import dk.easv.connor.mytunes.be.Playlist;
import dk.easv.connor.mytunes.be.Song;
import dk.easv.connor.mytunes.bll.Logic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DeleteConfirmationController
{
    @FXML
    private Button deleteConfirmButton;

    @FXML
    private Button deleteCancelButton;

    private Logic logic;
    private Song songToEdit;
    private HelloController parentController;
    private Playlist playlist;

    public void initialize()
    {
        logic = new Logic();
    }

    public void onDeleteConfirmButtonClick(ActionEvent actionEvent)
    {

    }

    public void onDeleteCancelButtonClick(ActionEvent actionEvent)
    {
        Stage stage = (Stage) deleteCancelButton.getScene().getWindow();
        stage.close();
    }
}
