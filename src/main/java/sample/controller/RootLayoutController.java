package sample.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

/**
 * Created by Marcin on 21.09.2017.
 */
public class RootLayoutController {
    // Exit the program
    public void handleExit(ActionEvent actionEvent) {
        System.exit(0);
    }
    // Help Menu button behavior
    public void handleHelp (ActionEvent actionEvent){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Program Information");
        alert.setHeaderText("To jest moja pierwsza aplikacja używająca bazę danych");
        alert.setContentText("Możesz szukać, usuwać, dodawać nowe gry w tej aplikacji");
        alert.show();
        // SOURCE --> http://www.swtestacademy.com/database-operations-javafx/
    }
}
