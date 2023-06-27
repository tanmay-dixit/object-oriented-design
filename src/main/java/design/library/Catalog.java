package design.library;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class Catalog {

    private final Set<Book> books;

    private Catalog(Set<Book> books) {
        this.books = new HashSet<>(books);
    }

    public static Catalog of(Set<Book> initialBooks) {
        Set<Book> validBooks = new HashSet<>();
        initialBooks.stream()
                .filter(Objects::nonNull)
                .forEach(validBooks::add);
        return new Catalog(validBooks);
    }

    public void addBook(Book newBook) throws Exception {
        Validate.objectIsNonNull(newBook, "Book");
        books.add(newBook);
    }

    public Set<Book> getAllBooks() {
        return Set.copyOf(books);
    }

    public Set<Book> findBookByTitle(String title) {
        return books.stream()
                .filter(book -> book.hasTitle(title))
                .collect(toSet());
    }

    public Set<Book> findBooksByAuthor(String author) {
        return books.stream()
                .filter(book -> book.hasAuthor(author))
                .collect(toSet());
    }

    public Set<Book> findBooksPublishedAfter(LocalDate expectedDate) {
        return books.stream()
                .filter(book -> book.wasPublishedAfter(expectedDate))
                .collect(toSet());
    }

    public Set<Book> findBooksPublishedBefore(LocalDate expectedDate) {
        return books.stream()
                .filter(book -> book.wasPublishedBefore(expectedDate))
                .collect(toSet());
    }

}
