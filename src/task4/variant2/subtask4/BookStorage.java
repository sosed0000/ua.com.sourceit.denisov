package task4.variant2.subtask4;

import task4.variant2.subtask4.books.Book;
import task4.variant2.subtask4.exeptions.ReadingRoomOnlyBookException;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BookStorage {
    private static final BookStorage instance = new BookStorage();
    private final Lock lock = new ReentrantLock();
    private final ConcurrentMap<Integer, Book> storage = new ConcurrentHashMap<>();
    private final ConcurrentMap<Integer, Book> booksInReadingRoom = new ConcurrentHashMap<>();
    private final ConcurrentMap<Integer, Book> borrowedBooks = new ConcurrentHashMap<>();
    private final Set<Integer> codesSet = new HashSet<>();

    private BookStorage() {
    }

    public static BookStorage getInstance() {
        return instance;
    }

    public ConcurrentMap<Integer, Book> getStorage() {
        return storage;
    }

    public Book takeBookOut(int code) throws ReadingRoomOnlyBookException {
        try {
            lock.lock();
            Book book = storage.get(code);
            if (book!= null) {
                if (book.isReadingRoomOnly())
                    throw new ReadingRoomOnlyBookException();
                borrowedBooks.put(code, book);
            }
            return storage.remove(code);
        } catch (ReadingRoomOnlyBookException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public Book takeBookToReadingRoom(int code) {
        try {
            lock.lock();
            Book book = storage.remove(code);
            if (book != null)
                booksInReadingRoom.put(code, book);
            return book;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void putBook(Book book) {
        try {
            lock.lock();
            int code = book.getCode();
            storage.put(code, book);
            codesSet.add(code);
            borrowedBooks.remove(code);
            booksInReadingRoom.remove(code);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

    }

    public Set<Integer> getCodesSet() {
        return codesSet;
    }
}
