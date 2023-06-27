package design.library;

import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.toSet;

public class Book {
    private final String isbn;
    private final String title;
    private final String author;
    private final int pages;
    private final Category category;
    private final String publisher;
    private final LocalDate publishedDate;
    private final Set<BookCopy> copies;

    public Book(String isbn,
                String title,
                String author,
                int pages,
                Category category,
                String publisher,
                LocalDate publishedDate) throws IllegalArgumentException {
        validate(isbn, title, author, pages, category, publisher, publishedDate);
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.category = category;
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

    public Category getCategory() {
        return category;
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

    private void validate(String isbn,
                          String title,
                          String author,
                          int pages,
                          Category category,
                          String publisher,
                          LocalDate publishedDate) throws IllegalArgumentException {
        Validate.stringIsNotBlank(isbn, "ISBN");
        Validate.stringIsNotBlank(title, "Title");
        Validate.stringIsNotBlank(author, "Author");
        Validate.stringIsNotBlank(publisher, "Publisher");
        Validate.intIsPositive(pages, "Pages");
        Validate.objectIsNonNull(category, "Category");
        Validate.objectIsNonNull(publishedDate, "Published Date");
    }

    public boolean hasTitle(String expectedTitle) throws IllegalArgumentException {
        Validate.stringIsNotBlank(expectedTitle, "Book Title");
        return title.equals(expectedTitle);
    }

    public boolean hasAuthor(String expectedAuthor) throws IllegalArgumentException {
        Validate.stringIsNotBlank(expectedAuthor, "Book Author");
        return author.equals(expectedAuthor);
    }

    public boolean wasPublishedBefore(LocalDate expectedDate) throws IllegalArgumentException {
        Validate.objectIsNonNull(expectedDate, "Published Date");
        return publishedDate.isBefore(expectedDate);
    }

    public boolean wasPublishedAfter(LocalDate expectedDate) throws IllegalArgumentException {
        Validate.objectIsNonNull(expectedDate, "Published Date");
        return publishedDate.isAfter(expectedDate);
    }

    public Set<BookCopy> getAllCopies() {
        return Set.copyOf(copies);
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

    public void addCopyAtLocation(BookLocation location) throws IllegalArgumentException {
        Validate.objectIsNonNull(location, "Book Location");
        BookCopy newCopy = new BookCopy(this, location);
        copies.add(newCopy);
    }

    public void removeCopy(int copyNumberToRemove) throws IllegalArgumentException, BookCantBeRemovedException {
        Validate.intIsPositive(copyNumberToRemove, "Copy Number");
        boolean removed = this.copies.removeIf(copy -> copy.getCopyNumber() == copyNumberToRemove);
        if (!removed) {
            throw new BookCantBeRemovedException("Book with Copy Number " + copyNumberToRemove + " does not exist");
        }
    }

    public Optional<LocalDate> nextAvailableAt() {
        return copies.stream()
                .min(Comparator.comparing(BookCopy::getNextIssuableDate))
                .map(BookCopy::getNextIssuableDate);
    }


    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject)
            return true;

        if (otherObject == null || this.getClass() != otherObject.getClass())
            return false;

        Book otherBook = (Book) otherObject;
        return this.isbn.equals(otherBook.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

}
