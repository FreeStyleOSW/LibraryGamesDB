package sample.util;

import com.sun.rowset.CachedRowSetImpl;
import java.sql.*;

/**
 * Created by Marcin on 21.09.2017.
 */
public class DBUtil {
    // Declare JDBC Driver
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    // Connection
    private static Connection conn = null;

    // String Połączenia
    // String connStr = "jdbc:oracle:thin:Username/Password@IP:Port/SID";
    // Username=HR, Password=HR, IP=localhost, IP=1521, SID=xe
    private static final String connStr = "jdbc:mysql://localhost:3306/librarygames";

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
        // Establish the Oracle Connection using Connection String
        try {
            conn = DriverManager.getConnection(connStr,"Kadimato","marcink1995");
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
            dbConnect();
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
    // DB Execute UpdateGame (For UpdateGame/Insert/Delete) Operation
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
            dbDisconnect();
        }
    }
    public static ResultSet  dbPreparedStatementExecuteQuery(String sqlstmt) throws SQLException, ClassNotFoundException {
        PreparedStatement pst = null;
        ResultSet rs = null;
        CachedRowSetImpl crs = null;
        try {
            dbConnect();
            pst = conn.prepareStatement(sqlstmt);
            rs = pst.executeQuery();
            crs = new CachedRowSetImpl();
            crs.populate(rs);
        }catch (SQLException e) {
            System.out.println("Problem accured at executeQuery (PreparedStatement): \n" + e);
            throw e;
        }finally {
            if (pst != null)pst.close();
            dbDisconnect();
        }
        return crs;
    }
}

