package task1;

import java.util.Arrays;

public class SubTask1 {
    public static void main(String[] args) {

        System.out.println("task1.SubTask1");
        System.out.println("args: " + Arrays.toString(args));
        double x;
        double y;
        try {
            if (args.length != 2) throw new Exception();
            x = Double.parseDouble(args[0]);
            y = Double.parseDouble(args[1]);
        } catch (Exception e) {
            System.out.println("Invalid arguments! Two numbers expected.");
            throw new RuntimeException(e);
        }

        System.out.printf("Result: %.1f\n\n", x + y);


    }

}
