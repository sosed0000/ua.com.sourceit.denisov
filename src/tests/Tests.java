package tests;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Tests {
    public static void main(String[] args) {
        Path path = Path.of("D:\\233");
        System.out.println(path);
        path = Path.of("D:\\233\\");
        System.out.println(path);
        path = Path.of("D:/233");
        System.out.println(path);
        path = Path.of("D:/233/");
        System.out.println(path);
        path = path.resolve("data.txt");
        System.out.println(path);

    }


}