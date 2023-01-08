package task4.variant2.subtask4;

import task4.variant2.subtask4.books.Book;
import task4.variant2.subtask4.exeptions.ReadingRoomOnlyBookException;

import java.util.ArrayList;
import java.util.List;

public class Reader extends Thread{
    protected BookStorage bookStorage;
    protected String name;
    protected List<Book> borrowedBooks = new ArrayList<>();

    public Reader(String name) {
        this.name = name;
        this.bookStorage = BookStorage.getInstance();
    }

    @Override
    public void run() {

    }
    public void takeBookToReadingRoom(int code){
        System.out.printf("%s wants to take book (book code: %d) to the reading room.\n", name, code);
        Book book = bookStorage.takeBookToReadingRoom(code);
        if (book == null){
            System.out.printf("%s couldn't get the book (book code: %d). The book is currently out of the library.\n", name, code);
        } else {
            borrowedBooks.add(book);
            System.out.printf("%s takes the book (book code: %d, book title: \"%s\") to the reading room.\n", name, code, book.getTitle());
        }
    }
    public void takeBookOut(int code){
        System.out.printf("%s wants to take book (book code: %d.) out\n", name, code);
        Book book = null;
        try {
            book = bookStorage.takeBookOut(code);
        } catch (ReadingRoomOnlyBookException e) {
            System.out.printf("%s couldn't get the book (book code: %d) out. The book is for reading room only.\n", name, code);
        }
        if (book == null){
            System.out.printf("%s couldn't get the book (book code: %d). The book is currently out of the library.\n", name, code);
        } else {
            borrowedBooks.add(book);
            System.out.printf("%s takes the book (book code: %d, book title: \"%s\") out.\n", name, code, book.getTitle());
        }
    }

    public void returnBookToLibrary(Book book){
        bookStorage.putBook(book);
        borrowedBooks.remove(book);
        System.out.printf("%s returned the book (book code: %d, book title: \"%s\") to the library.\n", name, book.getCode(), book.getTitle());
    }
    public void readBook(){}
}
