package task2.subtask3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Scanner;

public class GuessTheNumber {
    public static void main(String[] args) {
        Random random = new Random();
        int secretNumber = random.nextInt(100) + 1;
        int inputNumber;

        System.out.println("Вгадай число від 1 до 100! (q - вихід)");

        while (true) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            if (input.toLowerCase().trim().equals("q")) break;

            try {
                inputNumber = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Щось пійшло не так! Введи число від 1 до 100, або \"q\"");
                continue;
            }
            if (inputNumber < secretNumber) {
                System.out.println("Меньше");
            } else if (inputNumber > secretNumber) {
                System.out.println("Багато");
            } else {
                System.out.println("Молодець!");
                scanner.close();
                break;
            }

        }
    }
}
