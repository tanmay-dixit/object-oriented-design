package design.library;

import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

import static design.library.BookStatus.AVAILABLE;

public class BookCopy {
    private final Book book;
    private final int copyNumber;
    private BookLocation location;
    private Instant nextAvailableAt;
    private BookStatus status;
    private Queue<Reservation> reservations;

    public BookCopy(Book book,
                    int copyNumber,
                    BookLocation location) throws IllegalArgumentException {
        validate(book, copyNumber, location);
        this.book = book;
        this.copyNumber = copyNumber;
        this.location = location;
        this.nextAvailableAt = Instant.now();
        this.status = AVAILABLE;
        this.reservations = new ArrayDeque<>();
    }

    private void validate(Book book,
                          int copyNumber,
                          BookLocation location) throws IllegalArgumentException {
        Validate.objectIsNonNull(book, "Book");
        Validate.intIsPositive(copyNumber, "Copy Number");
        Validate.objectIsNonNull(location, "Book Location");
    }

    public int getCopyNumber() {
        return copyNumber;
    }

    public BookLocation getLocation() {
        return location;
    }

    public Instant getNextAvailableAt() {
        return nextAvailableAt;
    }

    public boolean availableToIssue() {
        return status.equals(AVAILABLE);
    }

    private boolean availableToReserve() {
        return !AVAILABLE.equals(status) && reservations.size() < 3;
    }

}
