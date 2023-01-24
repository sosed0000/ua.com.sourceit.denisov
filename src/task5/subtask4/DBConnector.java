package task5.subtask4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static final String DRIVER_NAME   = "com.mysql.cj.jdbc.Driver";
    private final String connectionString;
    private Connection connection;

    public DBConnector(String connectionString) {
        this.connectionString = connectionString;
    }

    public DBConnector(String url, String dbName, String userName, String password) {
        connectionString = url + dbName + "?user=" + userName + "&password=" + password;
    }

    public Connection getConnection(){
        try {
            Class.forName(DRIVER_NAME).newInstance();
            connection = DriverManager.getConnection(connectionString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
