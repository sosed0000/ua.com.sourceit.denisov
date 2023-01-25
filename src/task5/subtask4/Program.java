package task5.subtask4;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Program {
    public static String url = "jdbc:mysql://localhost:3306/";
    public static String dbName = "denisov_task5.1_db";
    public static String userName = "Guest";
    public static String password = "Qwe12345";

    public static void main(String[] args) {
        createTestDB();

        DBConnector connector = new DBConnector(url, dbName, userName, password);
        DBAccess dbAccess = new DBAccess(connector.getConnection());
        
        dbAccess.printOrderInfoByID(3);
        dbAccess.printOrdersNotMoreThanTotalAndQtyItems(900, 1);
        dbAccess.printTodayOrdersWithoutItem(6);
        dbAccess.createOrderWithAllTodayItems();
        dbAccess.deleteOrdersWithItemsCount(0);

        connector.close();
    }

    public static void createTestDB() {
        String fileSeparator = FileSystems.getDefault().getSeparator();
        File sqlFilesPath;
        try {
            sqlFilesPath = Paths.get((new File("")).getCanonicalPath(), fileSeparator,
                    String.join(fileSeparator, new String[]{"src", "task5", "subtask4", "TestDB"})).toFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File[] files = sqlFilesPath.listFiles(filename -> filename.getName().toLowerCase().endsWith(".sql"));
        if (files != null)
            try {
                DBConnector connector = new DBConnector(url, userName, password);
                Connection connection = connector.getConnection();
                Statement statement = connection.createStatement();
                List<String> queries = new ArrayList<>();
                queries.add("DROP DATABASE IF EXISTS  `denisov_task5.1_db` ;");
                queries.add("CREATE SCHEMA `denisov_task5.1_db` DEFAULT CHARACTER SET utf8 ;");
                queries.add("USE `denisov_task5.1_db` ;");
                for (String query : queries) {
                    statement.execute(query);
                }
                for (File file : files) {
                    List<String> script = Files.readAllLines(Path.of(sqlFilesPath.getCanonicalPath()).resolve(Path.of(file.getName())));
                    queries = new ArrayList<>();
                    StringBuilder queryBuilder = new StringBuilder();
                    for (String s : script) {
                        if (!s.startsWith("--")) {
                            queryBuilder.append(s).append(" ");
                        }
                        if (queryBuilder.toString().trim().endsWith(";")) {
                            queries.add(queryBuilder.toString());
                            queryBuilder = new StringBuilder();
                        }
                    }
                    for (String query : queries) {
                        statement.execute(query);
                    }
                }
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }

    }
}
