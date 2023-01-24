package task5.subtask4;

public class Program {
    public static void main(String[] args) {
        DBConnector connector = new DBConnector("jdbc:mysql://localhost:3306/", "task5.1_db", "Guest", "Qwe12345");
        DBAccess dbAccess = new DBAccess(connector.getConnection());

        dbAccess.printOrderInfoByID(3);
        dbAccess.printOrdersNotMoreThanTotalAndQtyItems(900, 1);
        dbAccess.printTodayOrdersWithoutItem(6);
        dbAccess.createOrderWithAllTodayItems();
        dbAccess.deleteOrdersWithItemsCount(0);

        connector.close();
    }
}
