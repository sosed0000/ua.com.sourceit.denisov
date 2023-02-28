package task1;

import java.util.Arrays;

public class SubTask5 {
    public static void main(String[] args) {

        System.out.println("task1.SubTask5");
        System.out.println("args: " + Arrays.toString(args));
        int n;
        try {
            if (args.length != 1) throw new Exception();
            n = Integer.parseInt(args[0]);
            if (n < 2) throw new Exception();
        } catch (Exception e) {
            System.out.println("Invalid arguments! Single positive integer greater than 2 expected.");
            throw new RuntimeException(e);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i <= n; i++) {
            if (i == 2 || i == 3) sb.append(i).append(" ");
            for (int j = 2; j <= (i / 2); j++) {
                if (i % j == 0) break;
                if (j == (i / 2) ) sb.append(i).append(" ");
            }
        }

        System.out.printf("Result: %s\n\n",sb);
    }
}
