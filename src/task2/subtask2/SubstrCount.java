package task2.subtask2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubstrCount {
    public static void main(String[] args)
    {
        System.out.println(substrCount("Good Golly Miss Molly", "ll", 0,0));
    }

    public static int substrCount(String input, String needle, int offset, int length)
    {
        if (input == null || needle == null)
        {
            System.out.println("Parameter is null!");
            return 0;
        }

        if (offset < 0 || length < 0 || (offset + length) >= input.length())
        {
            System.out.println("Invalid offset or length!");
            return 0;
        }

        int entriesCount = 0;
        input = input.substring(offset, offset + length);
        Pattern pattern = Pattern.compile(needle);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            entriesCount++;
        }

        return entriesCount;
    }
}
