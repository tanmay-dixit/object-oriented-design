package design.library;

import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.toSet;

public class Book {
    
    private final String isbn;
    private final String title;
    private final String author;
    private final int pages;
    private final Subject subject;
    private final String publisher;
    private final LocalDate publishedDate;
    private final Set<BookCopy> copies;

    public Book(String isbn,
                String title,
                String author,
                int pages,
                Subject subject,
                String publisher,
                LocalDate publishedDate) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.subject = subject;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.copies = new HashSet<>();
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPages() {
        return pages;
    }

    public Subject getSubject() {
        return subject;
    }

    public String getPublisher() {
        return publisher;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public Set<BookCopy> getCopies() {
        return Set.copyOf(copies);
    }

    public boolean hasTitle(String expectedTitle) {
        return title.equals(expectedTitle);
    }

    public boolean hasAuthor(String expectedAuthor) {
        return author.equals(expectedAuthor);
    }

    public boolean hasSubject(Subject expectedSubject) {
        return subject.equals(expectedSubject);
    }

    public boolean wasPublishedBefore(LocalDate expectedDate) {
        return publishedDate.isBefore(expectedDate);
    }

    public boolean wasPublishedAfter(LocalDate expectedDate) {
        return publishedDate.isAfter(expectedDate);
    }

    public boolean canBeIssued() {
        return copies.stream().anyMatch(BookCopy::canBeIssued);
    }

    public Set<BookCopy> getIssuableCopies() {
        return copies.stream().filter(BookCopy::canBeIssued).collect(toSet());
    }

    public boolean canBeReserved() {
        return copies.stream().anyMatch(BookCopy::canBeReserved);
    }

    public Set<BookCopy> getReservableCopies() {
        return copies.stream().filter(BookCopy::canBeReserved).collect(toSet());
    }

    public void addCopyAtLocation(BookLocation location) {
        BookCopy newCopy = new BookCopy(this, location);
        copies.add(newCopy);
    }

    public void removeCopy(int copyNumberToRemove) throws BookCannotBeRemovedException {
        boolean removed = this.copies.removeIf(copy -> copy.getCopyNumber() == copyNumberToRemove);
        if (!removed) {
            throw new BookCannotBeRemovedException("Book with Copy Number " + copyNumberToRemove + " does not exist");
        }
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject)
            return true;

        if (otherObject == null || this.getClass() != otherObject.getClass())
            return false;

        var otherBook = (Book) otherObject;
        return this.isbn.equals(otherBook.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

}
