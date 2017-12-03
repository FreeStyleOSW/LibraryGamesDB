package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import sample.model.*;
import sample.model.dialogs.AddDialog;
import sample.model.dialogs.SureDialog;
import sample.model.dialogs.UpdateDialog;

import java.sql.SQLException;

/**
 * Created by Marcin on 21.09.2017.
 */
public class Controller {
    @FXML
    private TextArea resultArea;
    @FXML
    private TextField gameIdText;
    @FXML
    private TableView gamesTable;

    @FXML
    private TableColumn<Game, Integer> gameIdColumn;
    @FXML
    private TableColumn<Game, String> gameNameColumn;
    @FXML
    private TableColumn<Game, String> gameDevelopColumn;
    @FXML
    private TableColumn<Game, Double> gamePriceColumn;

    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {
        gameIdColumn.setCellValueFactory(cellData -> cellData.getValue().game_idProperty().asObject());
        gameNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        gameDevelopColumn.setCellValueFactory(cellData -> cellData.getValue().developProperty());
        gamePriceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        loadTableFromDataBase();
    }

    @FXML
    private void loadTableFromDataBase() throws SQLException, ClassNotFoundException {
        try {
            ObservableList<Game> gameData = GameDAO.findAllGames();
            gamesTable.setItems(gameData);
            System.out.println("REFRESH TABLE");
        } catch (SQLException e) {
            System.out.println("Error accured while getting games information from DB.\n");
            throw e;
        }
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

    @FXML
    private void handleHelp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Program Information");
        alert.setHeaderText("This is my first Application using Data Base");
        alert.setContentText("You can search,delete or insert new Game! ENJOY!");
        alert.show();
        // SOURCE --> http://www.swtestacademy.com/database-operations-javafx/
    }

    @FXML
    private void loadGameWithInfo(Game game) throws ClassNotFoundException {
        if (game != null) {
            ObservableList<Game> gamesData = FXCollections.observableArrayList();
            gamesData.add(game);
            gamesTable.setItems(gamesData);
            resultArea.setText
                    ("Name: " + game.getName() + "\n" +
                            "Developer: " + game.getDevelop() +
                            "Cost: " + game.getPrice());
        } else {
            resultArea.setText("This Game does not exist !\n");
            gamesTable.setItems(null);
        }
    }

    @FXML
    private void searchGame() throws ClassNotFoundException, SQLException {
        try {
            if (gameIdText.getText().equals("")) {
                resultArea.setText("Search INFO:\nPlease select Game!");
                loadTableFromDataBase();
                return;
            }
            Game game = GameDAO.searchGame(gameIdText.getText());
            loadGameWithInfo(game);
        } catch (SQLException e) {
            e.printStackTrace();
            resultArea.setText("Error accurred while getting employee information from DB.\n" + e);
            throw e;
        }
    }

    @FXML
    private void deleteGame(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            if (gameIdText.getText().equals("")) {
                resultArea.setText("Delete INFO:\nPlease select Game!");
                return;
            }
            SureDialog sureDialog = new SureDialog();
            if (sureDialog.getResult() == true) {
                GameDAO.deleteGameWithId(gameIdText.getText());
                loadTableFromDataBase();
                resultArea.setText("Game deleted!" +
                        "\nGame: " + gameIdText.getText() + "\n"
                );
            } else {
                resultArea.setText("You don't agree! WHY !?");
            }
        } catch (SQLException e) {
            resultArea.setText("Problem accured while deleting game " + e);
            throw e;
        }
    }

    @FXML
    private void showDialogAddGame() throws SQLException, ClassNotFoundException {
        AddDialog addDialog = new AddDialog();
        Game game = addDialog.getAddingGame();
        if (game == null) return;
        if (game.getName().equals("") || game.getDevelop().equals("") || game.getPrice() == 0) {
            resultArea.setText("Please check information about game !");
            return;
        }
        loadTableFromDataBase();
        resultArea.setText("Insert INFO\nGame inserted!" +
                "\nGame: " + game.getName() +
                "\nDeveloper: " + game.getDevelop() +
                "\nPrice: " + game.getPrice()
        );
    }

    @FXML
    private void showDialogUpdateGame() throws SQLException, ClassNotFoundException {
        if (gameIdText.getText().equals("")) {
            resultArea.setText("Please! Select Game!");
            return;
        }
        UpdateDialog updateDialog = new UpdateDialog(gameIdText.getText());
        loadTableFromDataBase();
        if (updateDialog.getResultBoolen() == true) {
            resultArea.setText("Update INFO\nGame updated!\n" +
                    "Game: " + updateDialog.getUpdatingGame().getName() +
                    "\nDeveloper: " + updateDialog.getUpdatingGame().getDevelop() +
                    "\nPrice: " + updateDialog.getUpdatingGame().getPrice());
        }
    }

    @FXML
    private void setGameIdTextFromTable() {
        if (gamesTable.getSelectionModel().getSelectedItem() != null) {
            Game game = (Game) gamesTable.getSelectionModel().getSelectedItem();
            gameIdText.setText(String.valueOf(game.getGame_id()));
        }
    }
}