package sample.util;

import com.sun.rowset.CachedRowSetImpl;

import java.sql.*;

/**
 * Created by Marcin on 21.09.2017.
 */
public class DBUtil {
    // Declare JDBC Driver
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    // Connection
    private static Connection conn = null;

    // String Połączenia
    // String connStr = "jdbc:oracle:thin:Username/Password@IP:Port/SID";
    // Username=HR, Password=HR, IP=localhost, IP=1521, SID=xe
    private static final String connStr = "jdbc:oracle:thin:HR/HR@localhost:1521/xe";

    //Connect to DB
    public static void dbConnect() throws SQLException, ClassNotFoundException {
        // Setting Oracle JDBC Driver
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Gdzie jest twój Sterownik JDBC ?");
            e.printStackTrace();
            throw e;
        }

        System.out.println("Zarejestrowano Sterownik Oracle JDBC!");

        // Establish the Oracle Connection using Connection String
        try {
            conn = DriverManager.getConnection(connStr);
        }catch (SQLException e) {
            System.out.println("Connection Failed! Check output console" + e);
            e.printStackTrace();
            throw e;
        }
    }
    // Close Connection
    public static void dbDisconnect() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }catch (SQLException e){
            throw e;
        }
    }
    // DB Execute Query Operation
    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {
        // Declare statement, resultSet and CachedResultSet as null
        Statement stmt = null;
        ResultSet resultSet = null;
        CachedRowSetImpl crs = null;
        try {
            // Connect to DB (Establish Oracle Connection)
            System.out.println("Select statement: " + queryStmt + "\n");

            // Create statement
            stmt = conn.createStatement();

            // Execute select (query) operation
            resultSet = stmt.executeQuery(queryStmt);

            // CachedRowSet Implementation
            //In order to prevent "java.sql.SQLRecoverableException: Closed Connection: next" error
            //We are using CachedRowSet
            crs = new CachedRowSetImpl();
            crs.populate(resultSet);
        }catch (SQLException e){
            System.out.println("Problem accurred at executeQuery operation: " + e);
            throw  e;
        }finally {
            if (resultSet != null) resultSet.close();
            if (stmt != null) stmt.close();
            dbDisconnect();
        }
        return crs;
    }
    // DB Execute Update (For Update/Insert/Delete) Operation
    public static void dbExecuteUpdate (String sqlStmt) throws SQLException, ClassNotFoundException {
        // Declare statement as null
        Statement stmt = null;
        try {
            dbConnect();
            stmt = conn.createStatement();
            stmt.executeUpdate(sqlStmt);
        }catch (SQLException e) {
            System.out.println("Problem accured at executeUpdate operation: " + e);
            throw e;
        }finally {
            if (stmt != null){
                // Close statement
                stmt.close();
            }
            dbConnect();
        }
    }
}

