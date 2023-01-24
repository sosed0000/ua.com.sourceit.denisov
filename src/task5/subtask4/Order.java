package task5.subtask4;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Order{
    private int ID;
    private Date orderDate;
    private Map<Integer, Integer> items;

    public Order() {
        items = new HashMap<>();
    }
    public Order(Map<Integer, Integer> items) {
        this.items = items;
    }
    public void addItem(int itemID, int itemQty){
        items.put(itemID, itemQty);
    }

    public Map<Integer, Integer> getItems() {
        return items;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return String.format("ORDER #%08d", ID);
    }
}
