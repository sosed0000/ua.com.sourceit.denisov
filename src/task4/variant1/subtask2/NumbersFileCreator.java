package task4.variant1.subtask2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;


public class NumbersFileCreator {
    public static void main(String[] args) {
//Якщо потрібен отриманий файл, розкоментувати строки 50-54
        Path tempFile;
        try {
            tempFile = Files.createTempFile(null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile.toFile()));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile.toFile()))) {

            for (int i = 1; i <= Math.random() * 50 + 20; i++) {
                writer.write((int) (Math.random() * 1000) + " ");
                if (i % 10 == 0) writer.newLine();
            }
            writer.flush();

            StringBuilder data = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) data.append(line);

            List<Integer> numbers = Arrays.stream(data.toString().split(" "))
                    .map(Integer::parseInt).sorted().toList();

            writer.close();  //Не впевнений, що це потрібно. Думав, що наступний райтер не отримає доступу до файлу, якщо цього не буде,
                             // але якщо закоментувати, то все одно працює)
            //Далі якось не дуже гарно, але ж працює и зрозуміло, що робить.
            BufferedWriter writer1 = new BufferedWriter(new FileWriter(tempFile.toFile()));
            for (int i = 0; i < numbers.size(); i++) {
                writer1.write(numbers.get(i) + " ");
                if (i != 0 && i % 10 == 0) writer1.newLine();
            }
            writer1.flush();
            writer1.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        try {
//            Files.move(tempFile, Path.of("d:/file.txt"), StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}
