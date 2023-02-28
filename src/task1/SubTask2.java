package task1;

import java.util.Arrays;

public class SubTask2 {
    public static void main(String[] args) {

        System.out.println("task1.SubTask2");
        System.out.println("args: " + Arrays.toString(args));
        StringBuilder resultRaw = new StringBuilder();
        for (String str : args) resultRaw.append(str).append(" ");

        System.out.printf("Result: %s\n\n", resultRaw.toString().trim());

    }
}
