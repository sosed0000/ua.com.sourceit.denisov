import java.util.ArrayList;
import java.util.List;

public class Tests {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);

        list.iterator().remove();
        System.out.println(list);
    }


}