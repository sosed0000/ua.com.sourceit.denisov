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
            ResultSet rs = statement.executeQuery(
                    "SELECT orders.order_id, orders.order_date,  item.item_name, order_item.item_quantity, " +
                            "item_price, (item_price * order_item.item_quantity), item.item_description " +
                            "FROM orders " +
                            "JOIN order_item ON orders.order_id = order_item.order_id " +
                            "JOIN item ON order_item.item_id = item.item_id " +
                            "WHERE orders.order_id =" + orderID);
            if (!rs.next()) {
                System.out.println("Empty order or no such order");
                return;
            }
            String line = "--------------------------------------------------------------------------------";
            System.out.println();
            System.out.printf("ORDER #%08d dated %S%n", rs.getInt(1), rs.getDate(2));
            System.out.println(line);
            System.out.printf("|    %S    |  %S  | %6S |    %S   | %-30S |%n",
                    "item_name", "qty", "price", "sum", "description");
            System.out.println(line);
            double total = 0.0;
            do {
                total += rs.getFloat(6);
                String itemName = rs.getString(3);
                if (itemName.length() > 15) {
                    itemName = itemName.substring(0, 15);
                }
                String itemDescription = rs.getString(5);
                if (itemDescription.length() > 30) {
                    itemDescription = itemDescription.substring(0, 30);
                }
                System.out.printf("| %-15s | %5s | %6.2f | %8.2f | %-30s |%n",
                        itemName, rs.getInt(4), rs.getFloat(5), rs.getFloat(6), rs.getString(7));
            } while (rs.next());
            System.out.println(line);
            System.out.printf("TOTAL:%39.2f%n", total);
            System.out.println();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void printOrdersNotMoreThanTotalAndQtyItems(double total, int itemsQuantity) {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT orders.order_id,  COUNT(item.item_name) AS count_item, SUM(item_price * item_quantity) AS total\n" +
                            "FROM orders " +
                            "JOIN order_item ON orders.order_id = order_item.order_id " +
                            "JOIN item ON order_item.item_id = item.item_id " +
                            "GROUP BY orders.order_id " +
                            "HAVING total <= " + total + " AND count_item  = " + itemsQuantity);
            if (!rs.next()) {
                System.out.println("No matches");
                return;
            }
            System.out.println();
            System.out.printf("Orders with total not more than %.2f and items quantity = %d%n", total, itemsQuantity);
            String line = "---------------------------------------------------------------";
            System.out.println(line);
            do {
                System.out.printf("ORDER #%08d%n", rs.getInt(1));
            } while (rs.next());
            rs.close();
            System.out.println(line);
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printTodayOrdersWithoutItem(int itemID) {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT  orders.order_id, order_date, SUM(order_item.item_id = " + itemID + ") AS find_item " +
                            "FROM orders " +
                            "JOIN order_item ON orders.order_id = order_item.order_id " +
                            "WHERE order_date = '" + (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()) + "' " +
                            "GROUP BY orders.order_id " +
                            "HAVING find_item = 0");
            if (!rs.next()) {
                System.out.println("No matches");
                return;
            }
            System.out.println();
            System.out.printf("Today orders without item ID: %d%n", itemID);
            String line = "-------------------------------";
            System.out.println(line);
            do {
                System.out.printf("ORDER #%08d%n", rs.getInt(1));
            } while (rs.next());
            rs.close();
            System.out.println(line);
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createOrderWithAllTodayItems() {
        List<Order> orders = getOrdersByDate(new Date());
        Order orderWithAllItems = combineOrders(orders);

    }

    private List<Order> getOrdersByDate(Date date) {
        List<Order> orders = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT  orders.order_id, item_id, item_quantity " +
                            "FROM orders " +
                            "JOIN order_item ON orders.order_id = order_item.order_id " +
                            "WHERE order_date = '" + (new SimpleDateFormat("yyyy-MM-dd")).format(date) + "' " +
                            "ORDER BY order_id ");
            if (!rs.next()) {
                return new ArrayList<>();
            }
            int orderID = rs.getInt(1);
            int lastOrderID = orderID;
            Order order = new Order();
            order.setID(orderID);
            while (rs.next()) {
                orderID = rs.getInt(1);
                if (orderID != lastOrderID) {
                    orders.add(order);
                    order = new Order();
                    order.setID(orderID);
                }
                order.addItem(rs.getInt(2), rs.getInt(3));
                lastOrderID = orderID;
            }
            orders.add(order);

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private Order combineOrders(List<Order> orders) {
        Map<Integer, Integer> groupOrderItems = new HashMap<>();
        for (Order order : orders) {
            Map<Integer, Integer> w = order.getItems();
            for (Map.Entry<Integer, Integer> entry : order.getItems().entrySet()) {
                if (groupOrderItems.computeIfPresent(entry.getKey(), (k, v) -> v += entry.getValue()) == null) {
                    groupOrderItems.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return new Order(groupOrderItems);
    }

    public void putOrderToDB(Order order){
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO orders (order_date) VALUES (?)", new String[] {"order_id"});
            statement.setDate(1, java.sql.Date.valueOf((new SimpleDateFormat("yyyy-MM-dd")).format(new Date())));
            System.out.println(statement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
