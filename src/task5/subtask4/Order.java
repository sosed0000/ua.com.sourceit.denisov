package task5.subtask4;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Order{
    private int ID;
    private Date date;
    private final Map<Integer, Integer> items;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("ORDER #%08d", ID);
    }
}
