package task4.variant2.subtask3;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        int count = 10000;
        List<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            arrayList.add(i);
        }
        List<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            linkedList.add(i);
        }

        long t1 = System.currentTimeMillis();
        removeSecondValues(arrayList);
        long t2 = System.currentTimeMillis();
        System.out.printf("ArrayList  time (ms): %d\n", t2 - t1);

        t1 = System.currentTimeMillis();
        removeSecondValues(linkedList);
        t2 = System.currentTimeMillis();
        System.out.printf("LinkedList time (ms): %d\n", t2 - t1);

    }

    public static void removeSecondValues(List<?> list) {
        while (list.size() > 1) {
            for (int i = 0; i < list.size(); i++){
                list.remove(i);
            }
        }
    }
}
