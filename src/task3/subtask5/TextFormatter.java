package task3.subtask5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextFormatter {
    public static void main(String[] args) {

        String text = "Здійснити форматування  заданого тексту з вирівнюванням ліворуч.\n" +
                "Програма  повинна розбивати текст на рядки з довжиною, що не перевищує\n" +
                "заданої кількості символів. Якщо чергове слово не може розміститися у\n" +
                "поточному рядку, його необхідно переносити на наступний.";
        int stringLength = 33;
        System.out.println(formatText(text, stringLength));
    }

    public static String formatText(String text, int stringLength) {
        if (stringLength < 2) {
            System.out.println("Line length must be more than 1!");
            return null;
        }
        if (text == null) {
            System.out.println("The text is null");
            return null;
        }

        text = text.replaceAll("\n", " ");
        List<String> words = new ArrayList<>(Arrays.asList(text.split(" ")));
        StringBuilder formattedText = new StringBuilder(); //Сюди збирається форматований текст.
        int currentStringLength = 0;
        while (!words.isEmpty()) {
            //Опрацювання випадку, коли слово довше за довжину рядка.
            if (words.get(0).length() > stringLength)
            {
                String longWord = words.remove(0);
                //Дописуємо поточну строку
                formattedText.append(longWord, 0, stringLength - currentStringLength).append("\n");
                //Ділимо залишок довгого слова на частини за розміром з длину строки
                String regex = "(?<=\\G.{" + stringLength + "})";
                List<String> dividedWord = new ArrayList<>(Arrays.asList(longWord.substring(stringLength - currentStringLength).split(regex)));

                //Записуємо частини слова у результуючий рядок.
                currentStringLength = 0;
                while (dividedWord.size() > 0) {
                    if (dividedWord.get(0).length() == stringLength) {
                        formattedText.append(dividedWord.remove(0)).append("\n");
                    } else {
                        currentStringLength = dividedWord.get(0).length() + 1;
                        formattedText.append(dividedWord.remove(0)).append(" ");
                    }
                }
            }
            //Перевірка !words.isEmpty() потрібна, тому що якщо останнє слово тексту довше за довжину рядка, words буде порожній.
            if (!words.isEmpty()) {
                //Записуємо слова у результуючий рядок.
                if ( (currentStringLength + words.get(0).length()) < stringLength) {
                    currentStringLength += words.get(0).length() + 1;
                    formattedText.append(words.remove(0)).append(" ");
                } else if ( words.get(0).length() <= stringLength) {
                    currentStringLength = words.get(0).length() + 1;
                    formattedText = new StringBuilder(formattedText.toString().trim());
                    formattedText.append("\n").append(words.remove(0)).append(" ");
                }
            }
        }

        return formattedText.toString().trim();
    }

}
