package design.library;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.Queue;

public class BookCopy {

    private static final int MAX_RESERVATIONS = 3;
    private final Book book;
    private final int copyNumber;
    private final Queue<Reservation> reservations;
    private BookLocation location;
    private LocalDate nextIssuableDate;
    private Member issuer;

    public BookCopy(Book book,
                    int copyNumber,
                    BookLocation location) throws IllegalArgumentException {
        validate(book, copyNumber, location);
        this.book = book;
        this.copyNumber = copyNumber;
        this.location = location;
        this.nextIssuableDate = LocalDate.now();
        this.issuer = null;
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

    public LocalDate getNextIssuableDate() {
        return nextIssuableDate;
    }

    public boolean canBeIssued() {
        return issuer == null;
    }

    public boolean canBeReserved() {
        return !canBeIssued() && reservations.size() < MAX_RESERVATIONS;
    }

    public void updateLocation(BookLocation newLocation) {
        location = newLocation;
    }

    public void issueCopy(Member member, int days) throws OperationNotSupportedException {
        if (!canBeIssued()) {
            throw new OperationNotSupportedException("Book Copy already issued");
        }
        issuer = member;
        nextIssuableDate = LocalDate.now().plusDays(days);
    }

    public void returnCopy() throws OperationNotSupportedException {
        if (canBeIssued()) {
            throw new OperationNotSupportedException("Book hasn't been issued. Hence cannot be returned.");
        }
        issuer = null;
        nextIssuableDate = LocalDate.now();
    }

    public void addReservationFor(Member member) {
        var newReservation = new Reservation(member, this);
        reservations.add(newReservation);
    }

    public void removeReservationFor(Member member) {
        reservations.stream().dropWhile(reservation -> reservation.wasDoneBy(member) && reservation.wasDoneFor(this));
    }

}
