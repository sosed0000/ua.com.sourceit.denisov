package task3.subtask3;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TextSort {
    public static void main(String[] args) {
        String text = "Java 19 and Java 17 available now\n" +
                "Java 17 LTS is the latest long-term support release for the Java SE platform. JDK 19 and JDK 17 binaries are free to use in production and free to redistribute, at no cost, under the Oracle No-Fee Terms and Conditions.\n" +
                "\n" +
                "JDK 19 will receive updates under these terms, until March 2023 when it will be superseded by JDK 20.\n" +
                "\n" +
                "JDK 17 will receive updates under these terms, until at least September 2024.";
        char letter = 'r';
        text = adaptText(text);
        List<String> sortedWords = sortText(text, letter);
        sortedWords.forEach(System.out :: println);



    }
    //Метод видаляє усі символи, що не є літерами латинської абетки, та зайві пробіли.
    public static String adaptText(String text)
    {
        if (text == null) return null;
        return text.replaceAll("[^a-zA-Z]+", " ").replaceAll("\\s+", " ").trim();
    }

    public static List<String> sortText(String text, char letter)
    {
        if (text == null || !Character.isLetter(letter))
            return null;
        Comparator<String> comparator = (o1, o2) -> {
            //Порівняння за кількістю входжень літери
            int result;
            result = (int) (
                    o1.chars().filter(ch -> ch == letter).count() -
                    o2.chars().filter(ch -> ch == letter).count()
            );
            //Якщо кількість входжень літери однакова, то порівняння за абеткою
            if (result == 0) {
                result = o1.compareToIgnoreCase(o2);
            }
            return result;
        };

        List<String> words = Arrays.asList(text.split(" "));
        words.sort(comparator);
        return words;

    }
}
