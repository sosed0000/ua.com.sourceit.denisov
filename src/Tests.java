import java.util.ArrayList;
import java.util.List;

public class Tests {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);
                list.add(3);
//        list.add(4);
//        System.out.println(list.size());
//        System.out.println(list.set(2, 999));
//        list.remove(3);
        list.set(3, 77);
        list.add(88);
        System.out.println(list);
    }
}