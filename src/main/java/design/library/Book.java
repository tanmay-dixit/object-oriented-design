package design.library;

import java.time.Instant;
import java.util.*;

public class Book{

    private final String isbn;
    private final String title;
    private final String author;
    private final int pages;
    private final Category category;
    private final String publisher;
    private final Instant publishedDate;
    private final List<BookCopy> copies;
    private int lastCopyNumber;

    public Book(String isbn,
                String title,
                String author,
                int pages,
                Category category,
                String publisher,
                Instant publishedDate) throws IllegalArgumentException {
            validate(isbn, title, author, pages, category, publisher, publishedDate);
            this.isbn           = isbn;
            this.title          = title;
            this.author         = author;
            this.pages          = pages;
            this.category       = category;
            this.publisher      = publisher;
            this.publishedDate  = publishedDate;
            this.copies         = new ArrayList<>();
            this.lastCopyNumber = 0;
    }

    private void validate(String isbn,
                         String title,
                         String author,
                         int pages,
                         Category category,
                         String publisher,
                         Instant publishedDate) throws IllegalArgumentException {
        Validate.stringIsNotBlank(isbn, "ISBN");
        Validate.stringIsNotBlank(title, "Title");
        Validate.stringIsNotBlank(author, "Author");
        Validate.stringIsNotBlank(publisher, "Publisher");
        Validate.intIsPositive(pages, "Pages");
        Validate.objectIsNonNull(category, "Category");
        Validate.objectIsNonNull(publishedDate, "Published Date");
    }

    public String getTitle() {
        return title;
    }

    public boolean hasTitle(String expectedTitle) throws IllegalArgumentException {
        Validate.stringIsNotBlank(expectedTitle, "Book Title");
        return title.equals(expectedTitle);
    }

    public boolean hasAuthor(String expectedAuthor) throws IllegalArgumentException {
        Validate.stringIsNotBlank(expectedAuthor, "Book Author");
        return author.equals(expectedAuthor);
    }

    public boolean wasPublishedBefore(Instant expectedDate) throws IllegalArgumentException {
        Validate.objectIsNonNull(expectedDate, "Published Date");
        return publishedDate.isAfter(expectedDate);
    }

    public boolean wasPublishedAfter(Instant expectedDate) throws IllegalArgumentException {
        Validate.objectIsNonNull(expectedDate, "Published Date");
        return publishedDate.isBefore(expectedDate);
    }

    public List<BookCopy> getAllCopies() {
        return List.copyOf(copies);
    }

    public boolean canBeIssued() {
        return copies.stream().anyMatch(BookCopy::availableToIssue);
    }

    public List<BookCopy> getIssuableCopies() {
        return copies.stream().filter(BookCopy::availableToIssue).toList();
    }

    public boolean canBeReserved() {
        return copies.stream().anyMatch(BookCopy::availableToReserve);
    }

    public List<BookCopy> getReservableCopies() {
        return copies.stream().filter(BookCopy::availableToReserve).toList();
    }

    public synchronized boolean addCopyAtLocation(BookLocation location) throws IllegalArgumentException {
        Validate.objectIsNonNull(location, "Book Location");
        BookCopy newCopy = new BookCopy(this, ++lastCopyNumber, location);
        return copies.add(newCopy);
    }

    public synchronized void removeCopy(int copyNumberToRemove) throws IllegalArgumentException {
        Validate.intIsPositive(copyNumberToRemove, "Copy Number");
        boolean removed = this.copies.removeIf(copy -> copy.getCopyNumber() == copyNumberToRemove);
        if(!removed) {
            throw new IllegalArgumentException("Book with Copy Number " + copyNumberToRemove + " does not exist");
        }
    }

    public Instant nextAvailableAt() {
        return copies.isEmpty()
                ? Instant.MAX
                : Collections.min(copies, Comparator.comparing(BookCopy::getNextAvailableAt)).getNextAvailableAt();
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
