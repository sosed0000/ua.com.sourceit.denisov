package task4.variant2.subtask1;

public abstract class Book {
    public final int code;
    public final String author;
    public final String title;
    public final int publishedYear;
    public final String publisher;

    public Book(int code, String author, String title, int publishedYear, String publisher) {
        this.code = code;
        this.author = author;
        this.title = title;
        this.publishedYear = publishedYear;
        this.publisher = publisher;
    }

    public void printFullInfo() {
        System.out.println("Повна інформація:");
        System.out.printf("Назва: \"%s\"\n", title);
        System.out.printf("Автор: \"%s\"\n", author);
        System.out.printf("Шифр: \"%d\"\n", code);
        System.out.printf("Рік видавництва: \"%d\"\n", publishedYear);
        System.out.printf("Видавництво: \"%s\"\n", publisher);
    }

    public void printShortInfo() {}

}
