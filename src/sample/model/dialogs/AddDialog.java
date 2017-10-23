package sample.model.dialogs;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.model.Game;

import java.util.Optional;

public class AddDialog extends Dialog<Game> {
    Dialog<Game> dialog;
    Game addingGame;

    public AddDialog() {
        arrangeDialogElements();
    }

    public Game getAddingGame() {
        return addingGame;
    }

    private void arrangeDialogElements() {
        dialog = new Dialog<>();
        dialog.setTitle("Add New Game");

        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/sample/resources/gamesfolder.png"));

        ButtonType loginButtonType = new ButtonType("Add Game", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameGame = new TextField();
        TextField developerGame = new TextField();
        TextField priceGame = new TextField();
        nameGame.setPromptText("Game name");
        developerGame.setPromptText("Developer");
        priceGame.setPromptText("Price");

        grid.add(new Label("Game name:"), 0, 0);
        grid.add(nameGame, 1, 0);
        grid.add(new Label("Developer:"), 0, 1);
        grid.add(developerGame, 1, 1);
        grid.add(new Label("Price:"), 0, 2);
        grid.add(priceGame, 1, 2);

        Node addButton = dialog.getDialogPane().lookupButton(loginButtonType);
        addButton.setDisable(true);

        nameGame.textProperty().addListener((observable, oldValue, newValue) -> {
            addButton.setDisable(newValue.isEmpty());
            if (newValue.equals("")){
                addButton.setDisable(true);
                nameGame.setStyle("-fx-control-inner-background: lightcoral");
            }else {
                addButton.setDisable(false);
                nameGame.setStyle("-fx-control-inner-background: lightgreen");
            }
        });

        developerGame.textProperty().addListener((observable, oldValue, newValue) -> {
            addButton.setDisable(newValue.isEmpty());
            if (newValue.equals("")){
                addButton.setDisable(true);
                developerGame.setStyle("-fx-control-inner-background: lightcoral");
            }else {
                addButton.setDisable(false);
                developerGame.setStyle("-fx-control-inner-background: lightgreen");
            }
        });

        priceGame.textProperty().addListener((observable, oldValue, newValue) -> {
            addButton.setDisable(newValue.isEmpty());
            Double res = null;
            try {
                if (newValue.equals("")){
                    addButton.setDisable(true);
                    priceGame.setStyle("-fx-control-inner-background: lightcoral");
                }else {
                    addButton.setDisable(false);
                    res = Double.valueOf(newValue);
                    priceGame.setStyle("-fx-control-inner-background: lightgreen");
                }
            }catch (Exception e){
                addButton.setDisable(true);
                priceGame.setStyle("-fx-control-inner-background: lightcoral");
            }
        });


        dialog.getDialogPane().setContent(grid);
        Platform.runLater(() -> nameGame.requestFocus());
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Game
                        (
                                new SimpleStringProperty(nameGame.getText()),
                                new SimpleStringProperty(developerGame.getText()),
                                new SimpleDoubleProperty(Double.valueOf(priceGame.getText()))
                        );
            }
            return null;
        });
        Optional<Game> result = dialog.showAndWait();
        result.ifPresent(game -> {
            System.out.println(result.get());
            addingGame = result.get();
        });
    }
}
