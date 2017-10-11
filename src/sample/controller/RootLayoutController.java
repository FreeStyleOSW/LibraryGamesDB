package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;

/**
 * Created by Marcin on 21.09.2017.
 */
public class RootLayoutController {
    // Exit the program
    public void handleExit() {
        System.exit(0);
    }
    // Help Menu button behavior
    public void handleHelp (){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Program Information");
        alert.setHeaderText("To jest moja pierwsza aplikacja używająca bazę danych");
        alert.setContentText("Możesz szukać, usuwać, dodawać nowe gry w tej aplikacji");
        alert.show();
        // SOURCE --> http://www.swtestacademy.com/database-operations-javafx/
    }

    public void showDialogWindow(){
        final Stage dialog;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Dialog.fxml"));
            dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            Scene dialogScene = new Scene(loader.load(),320,220);
            dialog.setTitle("Add Game");
            dialog.setScene(dialogScene);
            dialog.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
