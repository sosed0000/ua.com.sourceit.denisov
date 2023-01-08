package task4.variant2.subtask4.books;

public class TestBook extends Book{
    public TestBook(int code) {
        super(code, null, "Book #" + code, 0, null, false);
        this.readingRoomOnly = (int) (Math.random() * 2) == 0;
    }

    @Override
    public void printShortInfo() {
        System.out.println(title);
    }
}
