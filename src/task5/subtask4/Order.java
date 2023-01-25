package task5.subtask4;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Order{
    private int ID;
    private Date date;
    private final Map<Item, Integer> items;

    public Order() {
        items = new HashMap<>();
    }
    public Order(Map<Item, Integer> items) {
        this.items = items;
    }
    public void addItem(Item item, int itemQty){
        items.put(item, itemQty);
    }

    public Map<Item, Integer> getItems() {
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
