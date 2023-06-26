package design.library;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record Catalog(
        List<Book> books,
        List<IssuedItem> issueHistory,
        List<ReservedItem> reservationHistory

) {


    public static Catalog of(List<Book> initialBooks) {
        List<Book> validBooks = new ArrayList<>();
        initialBooks.stream()
                .filter(Objects::nonNull)
                .forEach(validBooks::add);
        return new Catalog(validBooks, new ArrayList<>(), new ArrayList<>());
    }

    public void addBook(Book newBook) throws Exception {
        Validate.objectIsNonNull(newBook, "Book");
        books.add(newBook);
    }

    public List<Book> getAllBooks() {
        return List.copyOf(books);
    }

    public List<Book> findBookByTitle(String title) {

        return books.stream()
                .filter(book -> book.hasTitle(title))
                .toList();
    }

    public List<Book> findBooksByAuthor(String author) {
        return books.stream()
                .filter(book -> book.hasAuthor(author))
                .toList();
    }

    public List<Book> findBooksPublishedAfter(Instant date) {
        return books.stream()
                .filter(book -> book.wasPublishedBefore(date))
                .toList();
    }

    public List<Book> findBooksPublishedBefore(Instant date) {
        return books.stream()
                .filter(book -> book.wasPublishedAfter(date))
                .toList();
    }

}
