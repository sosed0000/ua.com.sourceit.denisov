package task4.variant2.subtask1;

public class ReferenceBook extends Book{
    private final int category;

    public ReferenceBook(int code, String author, String title, int publishedYear, String publisher, int category) {
        super(code, author, title, publishedYear, publisher);
        this.category = category;
    }

    @Override
    public void printFullInfo() {
        super.printFullInfo();
        System.out.printf("Категорія: \"%s\"\n", title);
    }

    @Override
    public void printShortInfo() {
        System.out.printf("Назва: \"%s\"\n", title);
        System.out.printf("Категорія: \"%s\"\n", category);
    }
}
