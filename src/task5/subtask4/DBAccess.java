package task5.subtask4;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class DBAccess {
    private final Connection connection;

    public DBAccess(Connection connection) {
        this.connection = connection;
    }

    public void printOrderInfoByID(int orderID) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT orders.order_id, order_date,  item_name, item_quantity, " +
                            "item_price, (item_price * item_quantity), item_description " +
                            "FROM orders " +
                            "JOIN order_item ON orders.order_id = order_item.order_id " +
                            "JOIN item ON order_item.item_id = item.item_id " +
                            "WHERE orders.order_id =" + orderID);
            if (!resultSet.next()) {
                System.out.println("Empty order or no such order");
                return;
            }
            String line = "--------------------------------------------------------------------------------";
            System.out.println();
            System.out.printf("ORDER #%08d dated %S%n", resultSet.getInt(1), resultSet.getDate(2));
            System.out.println(line);
            System.out.printf("|    %S    |  %S  | %6S |    %S   | %-30S |%n",
                    "item_name", "qty", "price", "sum", "description");
            System.out.println(line);
            double total = 0.0;
            do {
                total += resultSet.getFloat(6);
                String itemName = resultSet.getString(3);
                if (itemName.length() > 15) {
                    itemName = itemName.substring(0, 15);
                }
                String itemDescription = resultSet.getString(7);
                if (itemDescription.length() > 30) {
                    itemDescription = itemDescription.substring(0, 30);
                }
                System.out.printf("| %-15s | %5s | %6.2f | %8.2f | %-30s |%n",
                        itemName, resultSet.getInt(4), resultSet.getFloat(5), resultSet.getFloat(6), itemDescription);
            } while (resultSet.next());
            System.out.println(line);
            System.out.printf("TOTAL:%39.2f%n", total);
            System.out.println();
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void printOrdersNotMoreThanTotalAndQtyItems(double total, int itemsQuantity) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT orders.order_id,  COUNT(item.item_name) AS count_item, SUM(item_price * item_quantity) AS total " +
                            "FROM orders " +
                            "JOIN order_item ON orders.order_id = order_item.order_id " +
                            "JOIN item ON order_item.item_id = item.item_id " +
                            "GROUP BY orders.order_id " +
                            "HAVING total <= " + total + " AND count_item  = " + itemsQuantity);
            if (!resultSet.next()) {
                System.out.println("No matches");
                return;
            }
            System.out.println();
            System.out.printf("Orders with total not more than %.2f and items quantity = %d%n", total, itemsQuantity);
            String line = "---------------------------------------------------------------";
            System.out.println(line);
            do {
                System.out.printf("ORDER #%08d%n", resultSet.getInt(1));
            } while (resultSet.next());
            resultSet.close();
            System.out.println(line);
            System.out.println();
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printTodayOrdersWithoutItem(int itemID) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT  orders.order_id, order_date, SUM(item_id = " + itemID + ") AS find_item " +
                            "FROM orders " +
                            "JOIN order_item ON orders.order_id = order_item.order_id " +
                            "WHERE order_date = '" + (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()) + "' " +
                            "GROUP BY orders.order_id " +
                            "HAVING find_item = 0");
            if (!resultSet.next()) {
                System.out.println("No matches");
                return;
            }
            System.out.println();
            System.out.printf("Today orders without item ID: %d%n", itemID);
            String line = "-------------------------------";
            System.out.println(line);
            do {
                System.out.printf("ORDER #%08d%n", resultSet.getInt(1));
            } while (resultSet.next());
            System.out.println(line);
            System.out.println();
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createOrderWithAllTodayItems() {
        Map<Integer, Integer> items = getItemsFromOrdersByDate(new Date());
        Order orderWithAllItems = new Order(items);
        putOrderToDB(orderWithAllItems);
    }

    public Map<Integer, Integer> getItemsFromOrdersByDate(Date date) {
        Map<Integer, Integer> items = new HashMap<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT  item_id, item_quantity " +
                            "FROM orders " +
                            "JOIN order_item ON orders.order_id = order_item.order_id " +
                            "WHERE order_date = '" + (new SimpleDateFormat("yyyy-MM-dd")).format(date) + "' " +
                            "ORDER BY orders.order_id ");
            while (resultSet.next()) {
                int itemID = resultSet.getInt(1);
                int itemQty = resultSet.getInt(2);
                if (items.computeIfPresent(itemID, (k, v) -> v += itemQty) == null) {
                    items.put(itemID, itemQty);
                }
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }


    public void putOrderToDB(Order order) {
        try {
            PreparedStatement prepareStatement = connection.prepareStatement(
                    "INSERT INTO orders (order_date) VALUES (?)", new String[]{"order_id"});
            prepareStatement.setDate(1, java.sql.Date.valueOf((new SimpleDateFormat("yyyy-MM-dd")).format(new Date())));
            prepareStatement.executeUpdate();
            ResultSet resultSet = prepareStatement.getGeneratedKeys();
            int orderID;
            if (resultSet.next()) {
                orderID = resultSet.getInt(1);
            } else {
                throw new SQLException("Can't get orderID");
            }
            order.setID(orderID);
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT order_date FROM orders WHERE order_id =" + orderID);
            if (!resultSet.next()) {
                throw new SQLException("Can't get order date");
            }
            order.setDate(resultSet.getDate(1));
            putOrderItemsToDB(order);
            resultSet.close();
            statement.close();
            prepareStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void putOrderItemsToDB(Order order) {
        int orderID = order.getID();
        for (Map.Entry<Integer, Integer> entry : order.getItems().entrySet()) {
            try {
                Statement statement = connection.createStatement();
                statement.execute("INSERT INTO order_item (order_id, item_id, item_quantity)" +
                        "VALUES (" + orderID + ", " + entry.getKey() + ", " + entry.getValue() + ")");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteOrdersWithItemsCount(int itemsCount) {
        Set<Integer> ordersID = findOrdersWithItemsCount(itemsCount);
        ordersID.forEach(this::deleteOrderByID);

    }

    private Set<Integer> findOrdersWithItemsCount(int itemsCount) {
        Set<Integer> orders = new HashSet<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT orders.order_id " +
                    "FROM orders " +
                    "LEFT JOIN order_item ON orders.order_id = order_item.order_id " +
                    "GROUP BY order_id " +
                    "HAVING COUNT(item_id) = " + itemsCount);
            while (rs.next()) {
                orders.add(rs.getInt(1));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private void deleteOrderByID(int orderID) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM orders WHERE (order_id = " + orderID + ")");
            statement.execute("DELETE FROM order_item WHERE (order_id = " + orderID + ")");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
