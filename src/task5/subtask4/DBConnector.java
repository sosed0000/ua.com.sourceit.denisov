package task5.subtask4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector implements AutoCloseable {
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private final String url;
    private final String dbName;
    private final String userName;
    private final String password;
    private final String connectionString;
    private Connection connection;


    public DBConnector(String url, String dbName, String userName, String password) {
        this.url = url;
        this.dbName = dbName;
        this.userName = userName;
        this.password = password;
        connectionString = url + dbName + "?user=" + userName + "&password=" + password;
    }

    public DBConnector(String url, String userName, String password) {
        this.url = url;
        this.userName = userName;
        this.password = password;
        this.dbName = null;
        connectionString = null;
    }

    public Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (connectionString != null) {
            connection = DriverManager.getConnection(connectionString);
        } else {
            connection = DriverManager.getConnection(url, userName, password);
        }
        return connection;
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }


}
