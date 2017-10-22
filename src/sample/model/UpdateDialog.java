package sample.model;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;

public class UpdateDialog {
    Dialog<Game> dialog;
    String game_idText;
    Game game;
    Boolean resultBoolen = false;

    public UpdateDialog(String game_idText) throws SQLException, ClassNotFoundException {
        this.game_idText = game_idText;
        arrangeDialogElements();
    }

    public Boolean getResultBoolen() {
        return resultBoolen;
    }

    public Game getUpdatingGame(){
        return game;
    }

    private void arrangeDialogElements() throws SQLException, ClassNotFoundException {
        dialog = new Dialog<>();
        dialog.setTitle("Update Game");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/sample/resources/gamesfolder.png"));

        ButtonType updateButtonType = new ButtonType("Update Game", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

        game = GameDAO.searchGame(game_idText);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameGame = new TextField(game.getName());
        TextField developerGame = new TextField(game.getDevelop());
        TextField priceGame = new TextField(String.valueOf(game.getPrice()));
        nameGame.setPromptText("Game name");
        developerGame.setPromptText("Developer");
        priceGame.setPromptText("Price");

        grid.add(new Label("Game name:"), 0, 0);
        grid.add(nameGame, 1, 0);
        grid.add(new Label("Developer:"), 0, 1);
        grid.add(developerGame, 1, 1);
        grid.add(new Label("Price:"), 0, 2);
        grid.add(priceGame, 1, 2);

        Node UpdateButton = dialog.getDialogPane().lookupButton(updateButtonType);
        UpdateButton.setDisable(true);

        nameGame.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                game.setName(newValue);
                UpdateButton.setDisable(false);
                nameGame.setStyle("-fx-control-inner-background: lightgreen");
            }catch (Exception e){
                UpdateButton.setDisable(true);
                nameGame.setStyle("-fx-control-inner-background: lightcoral");
            }
        });

        developerGame.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                game.setDevelop(newValue);
                UpdateButton.setDisable(false);
                developerGame.setStyle("-fx-control-inner-background: lightgreen");
            }catch (Exception e) {
                UpdateButton.setDisable(true);
                developerGame.setStyle("-fx-control-inner-background: lightcoral");
            }
        });

        priceGame.textProperty().addListener((observable, oldValue, newValue) -> {
            Double res;
            try {
                res = Double.valueOf(newValue);
                UpdateButton.setDisable(false);
                game.setPrice(res);
                priceGame.setStyle("-fx-control-inner-background: lightgreen");
            }catch (Exception e){
                UpdateButton.setDisable(true);
                priceGame.setStyle("-fx-control-inner-background: lightcoral");
            };
        });

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButtonType){
                    resultBoolen = true;
                    return game;
            }
            resultBoolen = false;
            return null;
        });
        dialog.showAndWait().ifPresent(game -> {
            try {
                GameDAO.updateGame((Game) game);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

}
