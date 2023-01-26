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
        System.out.println((int) (Math.random() * 10 + 1));
        Date date = null;
        System.out.println(java.sql.Date.valueOf((new SimpleDateFormat("yyyy-MM-dd")).format(date)));
    }


}