package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.util.DBUtil;

import java.sql.*;

/**
 * Created by Marcin on 21.09.2017.
 */
public class GameDAO {
    public static void createTable() throws SQLException, ClassNotFoundException {
        String createTableStmt =
                "CREATE TABLE librarygames ( " +
                "GAME_ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                "NAME VARCHAR(255) NOT NULL, " +
                "DEVELOPER VARCHAR(255), " +
                "PRICE DOUBLE)";
        try {
            System.out.println(createTableStmt);
            DBUtil.dbExecuteUpdate(createTableStmt);
        }catch (SQLException e) {
            System.out.println("Error acccurred while INSERT Operation: " + e);
            throw e;
        }
    }
    private static Game getGameFromResultSet(ResultSet rs) throws SQLException {
        Game game = null;
        if (rs.next()) {
            game = new Game();
            game.setGame_id(rs.getInt("GAME_ID"));
            game.setName(rs.getString("NAME"));
            game.setDevelop(rs.getString("DEVELOPER"));
            game.setPrice(rs.getDouble("PRICE"));
        }
        return game;
    }
    public static Game searchGame (String gameId) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM librarygames WHERE GAME_ID = " + gameId;
        // Execute SELECT statement
        try {
            // Get ResultSet from dbExecuteQuery method
            ResultSet rsGame = DBUtil.dbExecuteQuery(selectStmt);
            // Send ResultSet to the getGameFromResultSet method and get addingGame object
            Game result = getGameFromResultSet(rsGame);
            // Return addingGame object
            return result;
        }catch (SQLException e){
            System.out.println("While searching a addingGame with " + gameId + " id, an error accurred: " + e);
            // Return exception
            throw e;
        }
    }
    public static void insertGame (Game game) throws SQLException, ClassNotFoundException {

        String updateStmt =
                "INSERT INTO librarygames " +
                        "(NAME, DEVELOPER, PRICE)" +
                        "VALUES" +
                        "('"+ game.getName() + "', '" + game.getDevelop() + "', '" + game.getPrice() +"'"+")";
        try {
            System.out.println(updateStmt);
            DBUtil.dbExecuteUpdate(updateStmt);
        }catch (SQLException e){
            System.out.println("Error acccurred while INSERT Operation: " + e);
            throw e;
        }
    }
    public static void updateGame (Game game) throws SQLException, ClassNotFoundException  {
        // Declare a UPDATE statement
        String updateStmt =
                "UPDATE librarygames\n" +
                        "    SET " +
                        "NAME = '" + game.getName() + "', " +
                        "DEVELOPER = '" + game.getDevelop() + "', " +
                        "PRICE = '" + game.getPrice() + "'" +
                        "\n" +
                        "    WHERE GAME_ID = " + game.getGame_id();
        // Execute UPDATE operation
        try {
            System.out.println(updateStmt);
            DBUtil.dbExecuteUpdate(updateStmt);
        }catch (SQLException e){
            System.out.println("Error accured while UPDATE Operation: " + e);
            throw e;
        }
    }
    public static void deleteGameWithId (String gameId) throws SQLException,ClassNotFoundException {
        // Declare a DELETE statement
        String updateStmt =
                "DELETE FROM librarygames\n" +
                        "        WHERE GAME_ID = " + gameId;
        // Execute DELETE Operation
        try {
            System.out.println(updateStmt);
            DBUtil.dbExecuteUpdate(updateStmt);
        }catch (SQLException e) {
            System.out.println("Error accured while DELETE Operation: " + e);
            throw e;
        }
    }
    private static ObservableList<Game> getGameList(ResultSet rs) throws SQLException,ClassNotFoundException{
        // Declare a observable List which comprises of Game Objects
        ObservableList<Game> gameList = FXCollections.observableArrayList();

        while (rs.next()){
            Game game = new Game();
            game.setGame_id(rs.getInt("GAME_ID"));
            game.setName(rs.getString("NAME"));
            game.setDevelop(rs.getString("DEVELOPER"));
            game.setPrice(rs.getDouble("PRICE"));
            gameList.add(game);
        }
        return gameList;
    }
    public static ObservableList<Game> findAllGames() throws SQLException, ClassNotFoundException {
        // Declare a SELECT statement
        String selectStmt = "SELECT * FROM librarygames";
        try {
            // Get ResultSet from dbExecuteQuery method
            ResultSet rsGames = DBUtil.dbExecuteQuery(selectStmt);

            // Send ResultSet to the getGamesList method and get addingGame object
            ObservableList<Game> gameList = getGameList(rsGames);

            //Return addingGame object
            return gameList;
        }catch (SQLException e){
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }
}
