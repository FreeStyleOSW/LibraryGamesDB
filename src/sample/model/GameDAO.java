package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.util.ConnectionConfiguration;
import sample.util.DBUtil;
import sun.plugin2.gluegen.runtime.CPU;

import java.sql.*;

/**
 * Created by Marcin on 21.09.2017.
 */
public class GameDAO {
    // ********************
    // SELECT a Game
    // ********************
    public static Game searchGame (String gameId) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM games WHERE GAME_ID = " + gameId;

        // Execute SELECT statement
        try {
            // Get ResultSet from dbExecuteQuery method
            ResultSet rsGame = DBUtil.dbExecuteQuery(selectStmt);
            // Send ResultSet to the getGameFromResultSet method and get game object
            Game game = getGameFromResultSet(rsGame);
            // Return game object
            return game;
        }catch (SQLException e){
            System.out.println("While searching a game with " + gameId + " id, an error accurred: " + e);
            // Return exception
            throw e;
        }
    }
    // Use ResultSet from DB as parameter and set Game Object's attributes and return game object.
    private static Game getGameFromResultSet(ResultSet rs) throws SQLException {
        Game game = null;
        if (rs.next()) {
            game = new Game();
            game.setGame_id(rs.getInt("GAME_ID"));
            game.setName(rs.getString("NAME"));
            game.setDevelop(rs.getString("DEVELOP"));
            game.setCost(rs.getDouble("COST"));
        }
        return game;
    }
    //***********************
    // SELECT Games
    //***********************
    public static ObservableList<Game> findAllGames() throws SQLException, ClassNotFoundException {
        // Declare a SELECT statement
        String selectStmt = "SELECT * FROM games";
        try {
            // Get ResultSet from dbExecuteQuery method
            ResultSet rsGames = DBUtil.dbExecuteQuery(selectStmt);

            // Send ResultSet to the getGamesList method and get game object
            ObservableList<Game> gameList = getGameList(rsGames);

            //Return game object
            return gameList;
        }catch (SQLException e){
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }
    // Select * from games operation
    private static ObservableList<Game> getGameList(ResultSet rs) throws SQLException,ClassNotFoundException{
        // Declare a observable List which comprises of Game Objects
        ObservableList<Game> gameList = FXCollections.observableArrayList();

        while (rs.next()){
            Game game = new Game();
            game.setGame_id(rs.getInt("GAME_ID"));
            game.setName(rs.getString("NAME"));
            game.setDevelop(rs.getString("DEVELOP"));
            game.setCost(rs.getDouble("COST"));
            gameList.add(game);
        }
        return gameList;
    }
    //****************************
    // UPDATE a game's cost
    //****************************
    public static void updateGameCost (String gameid,Double costGame) throws SQLException, ClassNotFoundException  {
        // Declare a UPDATE statement
        String updateStmt =
                        "UPDATE games\n" +
                        "    SET COST = '" + costGame + "'\n" +
                        "    WHERE GAME_ID = " + gameid;
        // Execute UPDATE operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        }catch (SQLException e){
            System.out.println("Error accured while UPDATE Operation: " + e);
            throw e;
        }
    }
    //****************
    // DELETE a Game
    //****************
    public static void deleteGameWithId (String gameId) throws SQLException,ClassNotFoundException {
        // Declare a DELETE statement
        String updateStmt =
                        "DELETE FROM games\n" +
                        "        WHERE game_id = " + gameId;
        // Execute DELETE Operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        }catch (SQLException e) {
            System.out.println("Error accured while DELETE Operation: " + e);
            throw e;
        }
    }
    public static int searchMaxGameID() throws  SQLException, ClassNotFoundException{
        // Szuka ostatniego najwyższego ID by później wykorzystać go przy dodawanie kolejnej gry
        int maxId = 1;
        try {
            String query = "SELECT MAX(GAME_ID) as maxid FROM games";
            ResultSet rs = DBUtil.dbPreparedStatementExecuteQuery(query);
            while (rs.next()){
                maxId = rs.getInt("maxid");
            }
            int nextId = maxId+1;
            System.out.println("MAX ID ----> " + maxId +" | NEXT ID ----> "  + nextId);
        }catch (SQLException e){
            System.out.println("Nie znaleziono ostatniego ID");
            e.printStackTrace();
        }
        return maxId + 1;
    }
    //**************************
    // INSERT a Game
    //**************************
    public static void insertGame (String name, String develop, Double cost) throws SQLException, ClassNotFoundException {

        String updateStmt =
                        "INSERT INTO games " +
                        "(GAME_ID, NAME, DEVELOP, COST)" +
                        "VALUES" +
                        "("+ searchMaxGameID() + ", '"+ name + "', '" + develop + "', " + cost +")";
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        }catch (SQLException e){
            System.out.println("Error acccurred while INSERT Operation: " + e);
            throw e;
        }
    }
}