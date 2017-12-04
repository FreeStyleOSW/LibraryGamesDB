package app.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Marcin on 25.09.2017.
 */
public class DBConnection {
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarygames","Kadimato","marcink1995");
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }
}
