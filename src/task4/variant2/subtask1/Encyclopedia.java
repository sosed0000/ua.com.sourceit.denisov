package task4.variant2.subtask1;

public class Encyclopedia extends Book{
    private final int volume;
    public Encyclopedia(int code, String author, String title, int publishedYear, String publisher, int volume) {
        super(code, author, title, publishedYear, publisher);
        this.volume = volume;
    }

    @Override
    public void printFullInfo() {
        System.out.println("Повна інформація:");
        System.out.printf("Назва: \"%s\"\n", title);
        System.out.printf("Том: \"%d\"\n", volume);
        System.out.printf("Автор: \"%s\"\n", author);
        System.out.printf("Шифр: \"%d\"\n", code);
        System.out.printf("Рік видавництва: \"%d\"\n", publishedYear);
        System.out.printf("Видавництво: \"%s\"\n", publisher);
    }

    @Override
    public void printShortInfo() {
        System.out.printf("Назва: \"%s\"\n", title);
        System.out.printf("Том: \"%d\"\n", volume);
        System.out.printf("Рік видавництва: \"%d\"\n", publishedYear);
    }
}
