package design.library;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class Catalog {

    private final Set<Book> books;

    public Catalog() {
        this.books = new HashSet<>();
    }

    private Catalog(Set<Book> books) {
        this.books = new HashSet<>(books);
    }

    public void addBook(Book newBook) {
        books.add(newBook);
    }

    public Set<Book> getAllBooks() {
        return Set.copyOf(books);
    }

    public Set<Book> findBooksByTitle(String title) {
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

    public Set<Book> findBooksBySubject(Subject subject) {
        return books.stream()
                .filter(book -> book.hasSubject(subject))
                .collect(toSet());
    }

}
