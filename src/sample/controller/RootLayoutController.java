package sample.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.Main;
import sample.model.Game;
import sample.model.GameDAO;

import java.sql.SQLException;
import java.util.Optional;
// TODO TOO MUCH TO DO !
// TODO Zrobić Rest do jakiejś biblioteki gier i pobrać je do swojej Bazy Danych
// TODO Design aplikacji
// TODO łączenie się z bazą danych dodać
// TODO Oczyscic Contorller , stworzyc nowe klasy
// TODO Dodać aliasy z githuba

/**
 * Created by Marcin on 21.09.2017.
 */
public class RootLayoutController {
    @FXML private TextArea resultArea;
    @FXML private TextField gameIdText;
    @FXML private TableView gamesTable;

    @FXML private TableColumn<Game,Integer> gameIdColumn;
    @FXML private TableColumn<Game,String> gameNameColumn;
    @FXML private TableColumn<Game,String> gameDevelopColumn;
    @FXML private TableColumn<Game,Double> gamePriceColumn;

    @FXML private void initialize () throws SQLException ,  ClassNotFoundException{
        gameIdColumn.setCellValueFactory(cellData -> cellData.getValue().game_idProperty().asObject());
        gameNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        gameDevelopColumn.setCellValueFactory(cellData -> cellData.getValue().developProperty());
        gamePriceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
//        loadTableFromDataBase();
        ObservableList<Game> gameData = GameDAO.findAllGames();
        gamesTable.setItems(gameData);
    }
    @FXML private void loadTableFromDataBase() throws SQLException, ClassNotFoundException {
        try {
            ObservableList<Game> gameData = GameDAO.findAllGames();
            gamesTable.setItems(gameData);
            System.out.println("REFRESH TABLE");
        }catch (SQLException e) {
            System.out.println("Error accured while getting games information from DB.\n");
            throw e;
        }
    }
    @FXML private void handleExit() {
        System.exit(0);
    }
    @FXML private void handleHelp (){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Program Information");
        alert.setHeaderText("This is my first Application using Data Base");
        alert.setContentText("You can search,delete or insert new Game! ENJOY!");
        alert.show();
        // SOURCE --> http://www.swtestacademy.com/database-operations-javafx/
    }
    @FXML private void loadGameWithInfo(Game game) throws ClassNotFoundException{
        if (game != null){
            ObservableList<Game> gamesData = FXCollections.observableArrayList();
            gamesData.add(game);
            gamesTable.setItems(gamesData);
            resultArea.setText
                    ("Name: " + game.getName() + "\n" +
                            "Developer: " + game.getDevelop() +
                            "Cost: " + game.getPrice());
        }else {
            resultArea.setText("This Game does not exist !\n");
            gamesTable.setItems(null);
        }
    }
    @FXML private void searchGame () throws ClassNotFoundException,SQLException{
        try {
            if (gameIdText.getText().equals("")) {
                resultArea.setText("Search INFO:\nPlease select Game!");
//                loadTableFromDataBase();
                return;
            }
            Game game = GameDAO.searchGame(gameIdText.getText());
            loadGameWithInfo(game);
        }catch (SQLException e) {
            e.printStackTrace();
            resultArea.setText("Error accurred while getting employee information from DB.\n" + e);
            throw e;
        }
    }
    @FXML private void insertGame (Game game) throws SQLException, ClassNotFoundException {
        try {
            if (game.getName().equals("") || game.getDevelop().equals("") || game.getPrice() == 0){
                resultArea.setText("Please check information about game !");
                return;
            }
            GameDAO.insertGame(game);
//            loadTableFromDataBase();
            resultArea.setText("Insert INFO\nGame inserted!" +
                    "\nGame: " + game.getName() +
                    "\nDeveloper: " + game.getDevelop() +
                    "\nPrice: " + game.getPrice()
            );
        }catch (SQLException e) {
            resultArea.setText("Problem accurred while inserting game " + e);
        }
    }
    @FXML private void deleteGame (ActionEvent actionEvent) throws SQLException,ClassNotFoundException{
        try {
            if (gameIdText.getText().equals("")) {
                resultArea.setText("Delete INFO:\nPlease select Game!");
                return;
            }
            GameDAO.deleteGameWithId(gameIdText.getText());
//            loadTableFromDataBase();
            resultArea.setText("Game deleted!" +
                    "\nGame: " + gameIdText.getText() + "\n"
            );
        }catch (SQLException e) {
            resultArea.setText("Problem accured while deleting game " + e);
            throw e;
        }
    }
    @FXML private void showDialogAddGame() {
        Dialog<Game> dialog = new Dialog<>();
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
            };
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
            try {
                insertGame(result.get());
//                loadTableFromDataBase();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML private void showDialogUpdateGame() throws SQLException, ClassNotFoundException {
        if (gameIdText.getText().equals("")) {
            resultArea.setText("UpdateGame INFO\nPlease select Game!");
            return;
        }
        Dialog dialog = new Dialog<>();
        dialog.setTitle("Update Game");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/sample/resources/gamesfolder.png"));

        ButtonType loginButtonType = new ButtonType("Update Game", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        String gameId = gameIdText.getText();
        Game game = GameDAO.searchGame(gameId);
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

        Node UpdateButton = dialog.getDialogPane().lookupButton(loginButtonType);
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
            Double res = null;
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
        dialog.show();
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType){
                try {
                    GameDAO.updateGame(game);
//                    loadTableFromDataBase();
                    gameIdText.setText("");
                }catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error! Update Game didn't work!");
                }
                resultArea.setText("Update INFO\nGame updated!\n" +
                        "Game: " + game.getName() +
                        "\nDeveloper: " + game.getDevelop() +
                        "\nPrice: " + game.getPrice()
                );
                return "You update The Game!";
            }
            return "You cancel Update! WHY?";
        });
    }
    @FXML private void setGameIdTextFromTable(){
        if (gamesTable.getSelectionModel().getSelectedItem() != null){
            Game game = (Game) gamesTable.getSelectionModel().getSelectedItem();
            gameIdText.setText(String.valueOf(game.getGame_id()));
        }
    }
}