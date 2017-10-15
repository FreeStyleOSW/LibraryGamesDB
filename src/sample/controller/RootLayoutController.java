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
import javafx.scene.layout.GridPane;
import sample.model.Game;
import sample.model.GameDAO;

import java.sql.SQLException;
import java.util.Optional;

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

    @FXML
    private void initialize () throws SQLException ,  ClassNotFoundException{
        gameIdColumn.setCellValueFactory(cellData -> cellData.getValue().game_idProperty().asObject());
        gameNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        gameDevelopColumn.setCellValueFactory(cellData -> cellData.getValue().developProperty());
        gamePriceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        loadTableFromDataBase();
    }
    @FXML
    private void loadTableFromDataBase() throws SQLException, ClassNotFoundException {
        try {
            // Get all games information
            ObservableList<Game> gameData = GameDAO.findAllGames();
            // Populate Games on TableView
            gamesTable.setItems(gameData);
        }catch (SQLException e) {
            System.out.println("Error accured while getting games information from DB.\n");
            throw e;
        }
    }
    @FXML
    private void handleExit() {
        System.exit(0);
    }
    @FXML
    private void handleHelp (){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Program Information");
        alert.setHeaderText("This is my first Application using Data Base");
        alert.setContentText("You can search,delete or insert new Game! ENJOY!");
        alert.show();
        // SOURCE --> http://www.swtestacademy.com/database-operations-javafx/
    }
    @FXML
    private void loadGameWithInfo(Game game) throws ClassNotFoundException{
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
    //    TODO Stworzyć nową klase Update.
    @FXML
    private void updateGameCost () throws SQLException, ClassNotFoundException {
        try {
            if (gameIdText.getText().equals("")) {
                resultArea.setText("Update INFO\nPlease select Game!");
                return;
            }else if (gameIdText.getText().equals("")){
                resultArea.setText("Please insert new Cost");
                return;
            }
            GameDAO.updateGameCost(gameIdText.getText(),Double.valueOf(gameIdText.getText()));
            loadTableFromDataBase();
            resultArea.setText("Cost has been updated for, game id: " + gameIdText.getText() + "\n");
        }catch (SQLException e){
            resultArea.setText("Problem accurred while updating cost: " + e);
            throw e;
        }
    }
    @FXML
    private void searchGame () throws ClassNotFoundException,SQLException{
        try {
            // Get Game information
            if (gameIdText.getText().equals("")) {
                resultArea.setText("Search INFO:\nPlease select Game!");
                loadTableFromDataBase();
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
    @FXML
    private void insertGame (Game game) throws SQLException, ClassNotFoundException {
        try {
            if (game.getName().equals("") || game.getDevelop().equals("") || game.getPrice() == 0){
                resultArea.setText("Please check information about game !");
                return;
            }
            GameDAO.insertGame(game);
            loadTableFromDataBase();
            resultArea.setText("Insert INFO\nGame inserted!\n" +
                    "Game: " + game.getName());
        }catch (SQLException e) {
            resultArea.setText("Problem accurred while inserting game " + e);
        }
    }
    @FXML
    private void deleteGame (ActionEvent actionEvent) throws SQLException,ClassNotFoundException{
        try {
            if (gameIdText.getText().equals("")) {
                resultArea.setText("Delete INFO:\nPlease select Game!");
                return;
            }
            GameDAO.deleteGameWithId(gameIdText.getText());
            loadTableFromDataBase();
            resultArea.setText("Game deleted! Game: " + gameIdText.getText() + "\n");
        }catch (SQLException e) {
            resultArea.setText("Problem accured while deleting game " + e);
            throw e;
        }
    }
//    TODO Dodać Validacje do TextField'ów
    @FXML
    private void showDialogAddGame() {
        Dialog<Game> dialog = new Dialog<>();
        dialog.setTitle("New game dialog");
        dialog.setHeaderText("Create new game");

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

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        //zachowanie przy zmianie wartości
        nameGame.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
        Platform.runLater(() -> nameGame.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
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
        result.ifPresent(usernamePassword -> {
            System.out.println(result.get());
            try {
                insertGame(result.get());
                loadTableFromDataBase();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
