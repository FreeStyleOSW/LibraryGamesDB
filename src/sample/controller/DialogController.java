package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.model.GameDAO;

import java.sql.SQLException;

/**
 * Created by Marcin on 04.10.2017.
 */
public class DialogController {
    @FXML
    private Button closebutton;
    @FXML private TextField nameText;
    @FXML private TextField developText;
    @FXML private TextField costText;

    public void closeWindow() {
        Stage stage = (Stage) closebutton.getScene().getWindow();
        stage.close();
    }
}
