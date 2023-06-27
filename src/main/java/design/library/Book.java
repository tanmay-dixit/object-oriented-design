package design.library;

import java.time.LocalDate;
import java.util.*;

public class Book {
    private static int lastCopyNumber = 0;
    private final String isbn;
    private final String title;
    private final String author;
    private final int pages;
    private final Category category;
    private final String publisher;
    private final LocalDate publishedDate;
    private final List<BookCopy> copies;

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
        this.copies = new ArrayList<>();
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

    public List<BookCopy> getCopies() {
        return copies;
    }

    public void validate(String isbn,
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

    public List<BookCopy> getAllCopies() {
        return List.copyOf(copies);
    }

    public boolean canBeIssued() {
        return copies.stream().anyMatch(BookCopy::canBeIssued);
    }

    public List<BookCopy> getIssuableCopies() {
        return copies.stream().filter(BookCopy::canBeIssued).toList();
    }

    public boolean canBeReserved() {
        return copies.stream().anyMatch(BookCopy::canBeReserved);
    }

    public List<BookCopy> getReservableCopies() {
        return copies.stream().filter(BookCopy::canBeReserved).toList();
    }

    public boolean addCopyAtLocation(BookLocation location) throws IllegalArgumentException {
        Validate.objectIsNonNull(location, "Book Location");
        BookCopy newCopy = new BookCopy(this, lastCopyNumber + 1, location);
        lastCopyNumber++;
        return copies.add(newCopy);
    }

    public void removeCopy(int copyNumberToRemove) throws IllegalArgumentException {
        Validate.intIsPositive(copyNumberToRemove, "Copy Number");
        boolean removed = this.copies.removeIf(copy -> copy.getCopyNumber() == copyNumberToRemove);
        if (!removed) {
            throw new IllegalArgumentException("Book with Copy Number " + copyNumberToRemove + " does not exist");
        }
    }

    public Optional<LocalDate> nextAvailableAt() {
        if (copies.isEmpty()) {
            return Optional.empty();
        }

        var nextAvailableCopy = Collections.min(copies, Comparator.comparing(BookCopy::getNextIssuableDate));
        var nextAvailableDate = nextAvailableCopy.getNextIssuableDate();
        return Optional.of(nextAvailableDate);
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
