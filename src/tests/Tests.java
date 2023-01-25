package tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Tests {
    public static void main(String[] args) throws IOException {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(4, 4);
        map.put(5, 5);
        Set<Integer> k = map.keySet();

        String s = Arrays.toString(k.toArray()).replaceFirst("\\[", "(").replace("]", ")");
        System.out.println(s);

    }


}