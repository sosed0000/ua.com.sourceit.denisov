package task1;

import java.util.Arrays;

public class SubTask4 {
    public static void main(String[] args) {

        System.out.println("task1.SubTask4");
        System.out.println("args: " + Arrays.toString(args));
        int x;
        try {
            if (args.length != 1) throw new Exception();
            x = Integer.parseInt(args[0]);
        } catch (Exception e) {
            System.out.println("Invalid arguments! Single integer expected.");
            throw new RuntimeException(e);
        }
        int sum = 0;
        while (x != 0) {
            sum += (x % 10);
            x /= 10;
        }

        System.out.printf("Result: %d\n\n",sum);

    }
}
