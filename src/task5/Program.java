package task5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Program {

    public static void main(String[] args) throws IOException {

        Program program = new Program("D:\\");

    }
    private final Path inputFile;
    private final Path outputFile;
    public Program(String path) throws IOException {
        Path p = Path.of(path);
        inputFile = p.resolve(Path.of("data.txt"));
        outputFile = p.resolve(Path.of("data_sorted.txt."));
        doWork();
    }

    private void doWork() throws IOException {
        createFile(inputFile);
        createFile(outputFile);
        fillFile(inputFile);
        int[] numbers = readFile(inputFile);
        sort(numbers);
        writeFile(outputFile, numbers);
        printResult(inputFile, outputFile);
    }

    private int[] readFile(Path inputFile) throws IOException {
//        Все одно буде тільки один рядок.
//        StringBuilder sb = new StringBuilder();
//        Files.readAllLines(inputFile).forEach(sb::append);
        return Arrays.stream(Files.readAllLines(inputFile).get(0).split(" "))
                .flatMapToInt(s -> IntStream.of(Integer.parseInt(s))).toArray();

    }


    private void createFile(Path inputFile) throws IOException {
        Files.deleteIfExists(inputFile);
        Files.createFile(inputFile);
    }

    private void fillFile(Path inputFile) throws IOException {
        StringBuilder numbers = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            numbers.append((int) (Math.random() * 50))
                    .append(" ");
        }
        Files.write(inputFile, List.of(numbers.toString()));
    }

    private void sort(int[] numbers){
        for (int out = numbers.length - 1; out >= 1; out--){
            for (int in = 0; in < out; in++){
                if(numbers[in] > numbers[in + 1]) {
                    int temp = numbers[in];
                    numbers[in] = numbers[in + 1];
                    numbers[in + 1] = temp;
                }
            }
        }
    }

    private void writeFile(Path outputFile, int[] numbers) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int number : numbers) {
            sb.append(number).append(" ");
        }
        Files.write(outputFile, List.of(sb.toString().trim()));
    }

    private void printResult(Path inputFile, Path outputFile) throws IOException {
        String input = Files.readAllLines(inputFile).get(0);
        String output = Files.readAllLines(outputFile).get(0);
        System.out.println("input  ==> " + input);
        System.out.println("output ==> " + output);
    }

}
