package task4.variant2.subtask4.books;

public abstract class Book {
    protected final int code;
    protected final String author;
    protected final String title;
    protected final int publishedYear;
    protected final String publisher;
    protected boolean readingRoomOnly = false;

    public Book(int code, String author, String title, int publishedYear, String publisher, Boolean readingRoomOnly) {
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

    public boolean isReadingRoomOnly() {
        return readingRoomOnly;
    }

    public void setReadingRoomOnly(boolean readingRoomOnly) {
        this.readingRoomOnly = readingRoomOnly;
    }

    public int getCode() {
        return code;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public String getPublisher() {
        return publisher;
    }
}
