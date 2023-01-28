package task5.subtask4;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

public class DBAccess {
    private final Connection connection;

    public DBAccess(Connection connection) {
        this.connection = connection;
    }

    public void printOrderInfoByID(int orderID) {
        System.out.printf("%nprintOrderInfoByID(orderID = %d) executing...%n", orderID);
        String query
                = "SELECT orders.order_id, order_date,  item_name, item_quantity, " +
                "item_price, (item_price * item_quantity) AS sum, item_description " +
                "FROM orders " +
                "JOIN order_item ON orders.order_id = order_item.order_id " +
                "JOIN item ON order_item.item_id = item.item_id " +
                "WHERE orders.order_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query);
             CallableStatement callableStatement = connection.prepareCall("CALL orderTotal(?, ?)")) {
            statement.setInt(1, orderID);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                System.out.println("Empty order or no such order");
                return;
            }
            String line = "--------------------------------------------------------------------------------";
            System.out.println();
            System.out.printf("ORDER #%08d dated %S%n", resultSet.getInt("orders.order_id")
                    , resultSet.getDate("order_date"));
            System.out.println(line);
            System.out.printf("|    %S    |  %S  | %6S |    %S   | %-30S |%n",
                    "item_name", "qty", "price", "sum", "description");
            System.out.println(line);
            do {
                String itemName = resultSet.getString("item_name");
                if (!resultSet.wasNull() && itemName.length() > 15) {
                    itemName = itemName.substring(0, 15);
                }
                String itemDescription = resultSet.getString("item_description");
                if (resultSet.wasNull()) {
                    itemDescription = "";
                } else if (itemDescription.length() > 30) {
                    itemDescription = itemDescription.substring(0, 30);
                }
                System.out.printf("| %-15s | %5s | %6.2f | %8.2f | %-30s |%n",
                        itemName, resultSet.getInt("item_quantity"), resultSet.getFloat("item_price")
                        , resultSet.getFloat("sum"), itemDescription);
            } while (resultSet.next());

            System.out.println(line);
            callableStatement.setInt(1, orderID);
            callableStatement.registerOutParameter(2, Types.FLOAT);
            resultSet = callableStatement.executeQuery();
            resultSet.next();
            double total = resultSet.getFloat("total");
            System.out.printf("TOTAL:%39.2f%n", total);
            System.out.println();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void printOrdersNotMoreThanTotalAndQtyItems(double total, int itemsQuantity) {
        System.out.printf("%nprintOrdersNotMoreThanTotalAndQtyItems(total = %f, itemsQuantity = %d) executing...%n", total, itemsQuantity);
        String query
                = "SELECT orders.order_id,  COUNT(item.item_name) AS count_item, SUM(item_price * item_quantity) AS total " +
                "FROM orders " +
                "JOIN order_item ON orders.order_id = order_item.order_id " +
                "JOIN item ON order_item.item_id = item.item_id " +
                "GROUP BY orders.order_id " +
                "HAVING total <= ? AND count_item  = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, total);
            statement.setInt(2, itemsQuantity);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                System.out.println("No matches");
                return;
            }
            System.out.println();
            System.out.printf("Orders with total not more than %.2f and items quantity = %d%n", total, itemsQuantity);
            String line = "---------------------------------------------------------------";
            System.out.println(line);
            do {
                System.out.printf("ORDER #%08d%n", resultSet.getInt("orders.order_id"));
            } while (resultSet.next());
            resultSet.close();
            System.out.println(line);
            System.out.println();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printTodayOrdersWithoutItem(int itemID) {
        System.out.printf("%nprintTodayOrdersWithoutItem(itemID = %d) executing...%n", itemID);
        String query = "SELECT  orders.order_id, SUM(item_id = ?) AS find_item " +
                "FROM orders " +
                "JOIN order_item ON orders.order_id = order_item.order_id " +
                "WHERE order_date = ? " +
                "GROUP BY orders.order_id " +
                "HAVING find_item = 0";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, itemID);
            statement.setDate(2, java.sql.Date.valueOf((new SimpleDateFormat("yyyy-MM-dd")).format(new Date())));
            ResultSet resultSet = statement.executeQuery();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createOrderWithAllTodayItems() {
        System.out.printf("%ncreateOrderWithAllTodayItems() executing...%n");
        Map<Item, Integer> items = getItemsFromOrdersByDate(new Date());
        if (items == null) {
            System.out.println("No orders today or all orders is empty");
            return;
        }
        Order orderWithAllItems = new Order(items);
        addOrderToDB(orderWithAllItems);

    }

    public Map<Item, Integer> getItemsFromOrdersByDate(Date date) {
        if (date == null) {
            return null;
        }
        Map<Integer, Integer> itemsIdAndQty = new HashMap<>();
        Map<Item, Integer> items = new HashMap<>();
        String query
                = "SELECT  item_id, item_quantity " +
                "FROM orders " +
                "JOIN order_item ON orders.order_id = order_item.order_id " +
                "WHERE order_date = ? " +
                "ORDER BY orders.order_id ";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, java.sql.Date.valueOf((new SimpleDateFormat("yyyy-MM-dd")).format(date)));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int itemID = resultSet.getInt("item_id");
                int itemQty = resultSet.getInt("item_quantity");
                if (itemsIdAndQty.computeIfPresent(itemID, (k, v) -> v += itemQty) == null) {
                    itemsIdAndQty.put(itemID, itemQty);
                }
            }

            if (itemsIdAndQty.size() == 0) {
                return null;
            }
            Set<Integer> itemsID = itemsIdAndQty.keySet();

            Set<Item> itemSet = getItemsFromDB(itemsID);
            for (Item item : itemSet) {
                items.put(item, itemsIdAndQty.get(item.getId()));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public Set<Item> getItemsFromDB(Set<Integer> itemsID) {
        if (itemsID == null) {
            return null;
        }
        if (itemsID.isEmpty()) {
            return new HashSet<>();
        }
        String query = String.format("SELECT  item_id, item_name, item_description, item_price " +
                "FROM item " +
                "WHERE item_id IN (%s)", itemsID.stream().map(v -> "?").collect(Collectors.joining(", ")));
        Set<Item> itemSet = new HashSet<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            int index = 1;
            for (int id : itemsID) {
                statement.setInt(index++, id);
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                itemSet.add(new Item(resultSet.getInt("item_id"), resultSet.getString("item_name")
                        , resultSet.getString("item_description"), resultSet.getFloat("item_price")));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemSet;
    }


    public void addOrderToDB(Order order) {
        assert (order != null);
        try (PreparedStatement prepareStatement = connection.prepareStatement(
                "INSERT INTO orders (order_date) VALUES (?)"
                , new String[]{"order_id"});
             Statement statement = connection.createStatement()) {

            connection.setAutoCommit(false);
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
            resultSet = statement.executeQuery(String.format("SELECT order_date FROM orders WHERE order_id = %d", orderID));
            if (!resultSet.next()) {
                throw new SQLException("Can't get order date");
            }
            order.setDate(resultSet.getDate("order_date"));
            addOrderItemsToDB(order);
            resultSet.close();
            System.out.printf("%s successfully added to DB%n%n", order);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
                System.out.println("Transaction failed");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    private void addOrderItemsToDB(Order order) throws SQLException {
        assert (order != null);
        int orderID = order.getID();
        for (Map.Entry<Item, Integer> entry : order.getItems().entrySet()) {
            String query
                    = "INSERT INTO order_item (order_id, item_id, item_quantity)" +
                    "VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, orderID);
            statement.setInt(2, entry.getKey().getId());
            statement.setInt(3, entry.getValue());
            statement.executeUpdate();

        }
    }

    public void deleteOrdersWithItemsCount(int itemsCount) {
        System.out.printf("%ndeleteOrdersWithItemsCount(itemsCount = %d) executing...%n", itemsCount);
        Set<Integer> ordersID = findOrdersWithItemsCount(itemsCount);
        if (ordersID.size() == 0) {
            System.out.println("No orders to delete");
            return;
        }
        ordersID.forEach(this::deleteOrderByID);
    }

    private Set<Integer> findOrdersWithItemsCount(int itemsCount) {
        Set<Integer> orders = new HashSet<>();
        String query
                = "SELECT orders.order_id " +
                "FROM orders " +
                "LEFT JOIN order_item ON orders.order_id = order_item.order_id " +
                "GROUP BY order_id " +
                "HAVING COUNT(item_id) = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, itemsCount);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orders.add(resultSet.getInt("orders.order_id"));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private void deleteOrderByID(int orderID) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(String.format("DELETE FROM orders WHERE (order_id = %d)", orderID));
            System.out.printf("ORDER #%08d successfully deleted from DB%n", orderID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTestOrder(int itemsInOrderCount) {
        assert (itemsInOrderCount >= 0);
        System.out.printf("Creating new order. Items in order: %d%n", itemsInOrderCount);
        List<Integer> itemsInDB = getAllItemsIdFromDB();
        Set<Integer> itemsInOrder = new HashSet<>();
        int itemsCount = itemsInDB.size();
        while (itemsInOrder.size() < itemsInOrderCount) {
            itemsInOrder.add(itemsInDB.get((int) (Math.random() * itemsCount)));
        }
        Set<Item> itemSet = getItemsFromDB(itemsInOrder);
        HashMap<Item, Integer> orderItems = new HashMap<>();
        for (Item item : itemSet) {
            orderItems.put(item, (int) (Math.random() * 10 + 1));
        }
        Order order = new Order(orderItems);
        addOrderToDB(order);
    }

    private List<Integer> getAllItemsIdFromDB() {
        List<Integer> itemsInDB = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT item_id FROM item");
            while (resultSet.next()) {
                itemsInDB.add(resultSet.getInt("item_id"));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemsInDB;
    }
}
