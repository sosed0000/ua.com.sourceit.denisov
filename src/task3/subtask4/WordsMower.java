package task3.subtask4;

public class WordsMower {
    public static void main(String[] args) {
        String sentence = "Ехал Грека через реку, видит Грека в реке Грека.\n" +
                " Сунул Грека Греку в Греку. Грека Грека Грека Грека";
        String lastWord = sentence.substring(sentence.lastIndexOf(" ") + 1);
        sentence = lastWord + " " + sentence.substring(0, sentence.lastIndexOf(lastWord) - 1);
        System.out.println(sentence);
    }
}
