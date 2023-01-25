package tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Tests {
    public static void main(String[] args) throws IOException {
        Path path = Path.of("C:\\Users\\Admin\\IdeaProjects\\ua.com.sourceit.denisov\\src\\task5\\subtask4\\TestDB");
      //  Tests tests = new Tests();
        File f = new File((new Tests()).getClass().getResource("/").getPath());
        System.out.println(f);
        File directory = new File (""); // Параметр пуст
        String courseFile = directory.getCanonicalPath();
        System.out.println(courseFile);
        String fileSeparator = FileSystems.getDefault().getSeparator();
        File file = Paths.get((new File ("")).getCanonicalPath(), fileSeparator, String.join(fileSeparator, new String[]{"src", "task5", "subtask4", "TestDB"})).toFile();
        System.out.println(file);

    }


}