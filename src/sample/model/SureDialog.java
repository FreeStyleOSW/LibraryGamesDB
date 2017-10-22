package sample.model;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Optional;

public class SureDialog {
    Dialog<Boolean> dialog;
    private Boolean resultBoolean;

    public SureDialog() {
        arrangeDialogElements();
    }

    private void setResult(Boolean result){
        this.resultBoolean = result;
    }

    public Boolean getResult(){
        return resultBoolean;
    }

    private void arrangeDialogElements(){
        dialog = new Dialog<>();
        dialog.setTitle("Warning!");

        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/sample/resources/gamesfolder.png"));

        ButtonType yesButtonType = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButtonType = new ButtonType("No", ButtonBar.ButtonData.NO);
        Node yesButton = dialog.getDialogPane().lookupButton(yesButtonType);
        Node noButton = dialog.getDialogPane().lookupButton(noButtonType);
        dialog.getDialogPane().getButtonTypes().addAll(yesButtonType,noButtonType);
        BorderPane borderPane = new BorderPane();
        Label areYouSure = new Label("Are you sure ?");
        areYouSure.setFont(Font.font(15));
        borderPane.setCenter(areYouSure);
        dialog.getDialogPane().setContent(borderPane);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == yesButtonType) {
                return true;
            }else if (dialogButton == noButtonType){
                return false;
            }
            return null;
        });
        dialog.showAndWait().ifPresent(result -> {
            setResult(result);
        });
    }
}
