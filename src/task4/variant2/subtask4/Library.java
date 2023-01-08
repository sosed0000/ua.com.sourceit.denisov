package task4.variant2.subtask4;

import task4.variant2.subtask4.books.TestBook;

import java.util.ArrayList;
import java.util.List;

public class Library {
    public static void main(String[] args) throws InterruptedException {
        BookStorage library = BookStorage.getInstance();
        for (int i = 0; i < 20; i++) {
            library.putBook(new TestBook(i));
        }
        List<Reader> readers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            readers.add(new ReaderBot());
        }
        for (Reader reader : readers){
            reader.setDaemon(true);
            reader.start();
            Thread.sleep(100);
        }
        Thread.sleep(5000);

    }
}
