package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.model.Game;
import sample.model.GameDAO;

import java.sql.SQLException;

/**
 * Created by Marcin on 21.09.2017.
 */
public class GameController {
    @FXML private TextArea resultArea;
    @FXML private TextField newCostText;
    @FXML private TextField gameIdText;
    @FXML private TextField nameText;
    @FXML private TextField developText;
    @FXML private TextField costText;
    @FXML private TableView gamesTable;

    @FXML private TableColumn<Game,Integer> gameIdColumn;
    @FXML private TableColumn<Game,String> gameNameColumn;
    @FXML private TableColumn<Game,String> gameDevelopColumn;
    @FXML private TableColumn<Game,Double> gameCostColumn;

    // Search a game
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
            // Populate Game on TableView and Display on TextArea
            loadGameWithInfo(game);
        }catch (SQLException e) {
            e.printStackTrace();
            resultArea.setText("Error accurred while getting employee information from DB.\n" + e);
            throw e;
        }
    }
    // Search all games
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
    // Initializing the controller class.
    // This method is automatically called after the fxml file has been loaded.
    @FXML
    private void initialize () throws SQLException ,  ClassNotFoundException{
        gameIdColumn.setCellValueFactory(cellData -> cellData.getValue().game_idProperty().asObject());
        gameNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        gameDevelopColumn.setCellValueFactory(cellData -> cellData.getValue().developProperty());
        gameCostColumn.setCellValueFactory(cellData -> cellData.getValue().costProperty().asObject());
        loadTableFromDataBase();
    }
    @FXML
    private void loadGameToTable (Game game) throws ClassNotFoundException {
        // Declare and ObservableList for table view
        ObservableList<Game> gamesData = FXCollections.observableArrayList();
        // Add game to ObservableList
        gamesData.add(game);
        // Set items to the gameTable
        gamesTable.setItems(gamesData);
    }
    // Set Game information to Text Area
    @FXML
    private void setGameInfoToTextArea (Game game) throws ClassNotFoundException {
        resultArea.setText
                ("Name: " + game.getName() + "\n" +
                 "Developer: " + game.getDevelop() +
                 "Cost: " + game.getCost());
    }
    // Populate Game for TableView and Display Game on TextArea
    @FXML
    private void loadGameWithInfo(Game game) throws ClassNotFoundException{
        if (game != null){
            loadGameToTable(game);
            setGameInfoToTextArea(game);
        }else {
            resultArea.setText("This Game does not exist !\n");
            gamesTable.setItems(null);
        }
    }
    @FXML
    private void updateGameCost () throws SQLException, ClassNotFoundException {
        try {
            if (gameIdText.getText().equals("")) {
                resultArea.setText("Update INFO\nPlease select Game!");
                return;
            }else if (newCostText.getText().equals("")){
                resultArea.setText("Please insert new Cost");
                return;
            }
            GameDAO.updateGameCost(gameIdText.getText(),Double.valueOf(newCostText.getText()));
            resultArea.setText("Cost has been updated for, game id: " + gameIdText.getText() + "\n");
        }catch (SQLException e){
            resultArea.setText("Problem accurred while updating cost: " + e);
            throw e;
        }
    }
    @FXML
    private void insertGame (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            if (nameText.getText().equals("") || developText.getText().equals("") || costText.getText().equals("")){
                resultArea.setText("Please check information about game !");
                return;
            }
            GameDAO.insertGame(nameText.getText(),developText.getText(),Double.valueOf(costText.getText()));
            loadTableFromDataBase();
            resultArea.setText("Insert INFO\nGame inserted!\n" +
                    "Game: " + nameText.getText());
        }catch (SQLException e) {
            resultArea.setText("Problem accurred while inserting game " + e);
        }
    }
    //Delete a game with a given game id from DB
    @FXML
    private void deleteGame (ActionEvent actionEvent) throws SQLException,ClassNotFoundException{
        try {
            if (gameIdText.getText().equals("")) {
                resultArea.setText("Delete INFO:\nPlease select Game!");
                return;
            }
            GameDAO.deleteGameWithId(gameIdText.getText());
            resultArea.setText("Game deleted! Game: " + gameIdText.getText() + "\n");
        }catch (SQLException e) {
            resultArea.setText("Problem accured while deleting game " + e);
            throw e;
        }
    }
}
