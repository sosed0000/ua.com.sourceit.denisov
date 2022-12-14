package task3.subtask2;

public class TextEncryption {

    public static void main(String[] args) {
        String text = "0123456789abcdefg";
        text = encrypt(text);
        System.out.println(text);
        text = decrypt(text);
        System.out.println(text);

    }
    public static String encrypt(String text)
    {
        if (text == null) return null;
        StringBuilder encryptedText = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = i; j < text.length(); j+=3) {
                encryptedText.append(text.charAt(j));
            }
        }
        return encryptedText.toString();
    }

    /* Логіка методу.
    Ділимо текст на три частини. Якщо залишок від ділення 1, то перша частина більша на 1 символ,
    якщо 2 -друга частина теж більше на один символ.
    Потім збираємо дешифрований текст забираючи по одному символу по черзі з кожної частини.
     */
    public static String decrypt(String text)
    {
        if (text == null) return null;

        int range1length = text.length() / 3;
        int range2length = text.length() / 3;
        int range3length = text.length() / 3;
        if (text.length() % 3 > 0)
            range1length++;
        if (text.length() % 3 == 2)
            range2length++;
        String range1 = text.substring(0, range1length);
        String range2 = text.substring(range1length, range1length + range2length);
        String range3 = text.substring(range1length + range2length);

        StringBuilder decryptedText = new StringBuilder();
        for (int i = 0; i < range3length; i++) {
            decryptedText.append(range1.charAt(i)).append(range2.charAt(i)).append(range3.charAt(i));
        }
        if (range1length > range3length)
            decryptedText.append(range1.charAt(range1length - 1));
        if (range2length > range3length)
            decryptedText.append(range2.charAt(range2length - 1));

        return decryptedText.toString();
    }

}


