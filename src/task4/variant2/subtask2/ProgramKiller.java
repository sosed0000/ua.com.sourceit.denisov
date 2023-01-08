package task4.variant2.subtask2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ProgramKiller {

    public static void main(String[] args) {
        Path sourceFile;
        try (BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Input full *.java file to kill path");
            sourceFile = Path.of(consoleReader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Path tempFile;
        try {
            tempFile = Files.createTempFile(null, null);
        } catch (IOException e) {
            System.err.println("Failed to create temporary file");
            throw new RuntimeException(e);
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile.toFile()));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile.toFile()))) {
            String line;
            while (reader.ready()) {
                line = reader.readLine();
                line = line.replaceAll("\\bpublic\\b", "private");
                writer.write(line);
                writer.newLine();
            }
            writer.flush();

        } catch (FileNotFoundException e) {
            System.err.println("File not found");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Files.move(tempFile, sourceFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Program " + sourceFile.getFileName() + " successfully killed! (all \"public\" modifiers replaced with \"private\")");


    }

}
