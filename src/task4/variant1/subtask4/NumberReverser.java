package task4.variant1.subtask4;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class NumberReverser {
    public static void main(String[] args) {
        Stack<Character> stack = new Stack<>();
        try(Scanner scanner = new Scanner(System.in)) {
            System.out.print("Input number: ");

//            String[] digits = scanner.nextLine().split("");
//            Arrays.stream(digits).map(Integer::parseInt).forEach(stack::add);
            String number = scanner.nextLine();
            for (Character ch : number.toCharArray())
                stack.push(ch);

            System.out.print("Reversed number: ");
            while (!stack.empty())
                System.out.print(stack.pop());
        }
    }
}
