package tests;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Tests {
    public static void main(String[] args) {
        ConcurrentMap<Integer, String> map = new ConcurrentHashMap<>();
        map.put(1, "sss");
        map.put(2, "eee");
        map.put(3, "eee");
        map.put(4, "eee");
        map.put(5, "eee");
        map.remove(7);
        Integer[] codes = new Integer[map.size()];
        List<Integer> list = map.keySet().stream().toList();
        System.out.println(list);


    }


}