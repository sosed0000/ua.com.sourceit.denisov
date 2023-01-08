package task4.variant2.subtask4;

import task4.variant2.subtask4.books.Book;

import java.util.List;

public class ReaderBot extends Reader {
    private static int countInstance = 0;

    public ReaderBot() {
        super("Reader #" + ++countInstance);
    }

    @Override
    public void run() {
        while (true){
            int i = (int) (Math.random() * 3 + 1);
            do {
                takeBookOut(0);
                i--;
            } while (i > 0);
            i = (int) (Math.random() * 3 + 1);
            do {
                takeBookToReadingRoom(0);
                i--;
            } while (i > 0);
            while (borrowedBooks.size() > 0) {
                readBook();
                returnBookToLibrary(borrowedBooks.get(0));
            }
        }
    }

    @Override
    public void takeBookToReadingRoom(int code) {
        List<Integer> codes = bookStorage.getCodesSet().stream().toList();
        code = codes.get((int) (Math.random() * codes.size()));
        super.takeBookToReadingRoom(code);
    }

    @Override
    public void takeBookOut(int code) {
        List<Integer> codes = bookStorage.getStorage().keySet().stream().toList();
        code = codes.get((int) (Math.random() * codes.size()));
        super.takeBookOut(code);
    }

    @Override
    public void returnBookToLibrary(Book book) {
        super.returnBookToLibrary(book);
    }

    @Override
    public void readBook() {
        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
