package task1;

import java.util.Arrays;

public class SubTask3 {
    public static void main(String[] args) {

        System.out.println("task1.SubTask3");
        System.out.println("args: " + Arrays.toString(args));
        int x;
        int y;
        try {
            if (args.length != 2) throw new Exception();
            x = Integer.parseInt(args[0]);
            y = Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.out.println("Invalid arguments! Two integers expected.");
            throw new RuntimeException(e);
        }
        while (x != y) {
            if (x > y) {
                x = x - y;
            } else {
                y = y - x;
            }
        }

        System.out.printf("Result: %d\n\n", x);

    }

}
