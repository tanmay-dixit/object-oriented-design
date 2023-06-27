package design.library;

import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.Queue;

public class BookCopy {

    private static final int MAX_RESERVATIONS = 3;
    private static int lastCopyNumber = 1;
    private final Book book;
    private final int copyNumber;
    private final Queue<Reservation> reservations;
    private BookLocation location;
    private LocalDate nextIssuableDate;
    private Member issuer;

    public BookCopy(Book book,
                    BookLocation location) throws IllegalArgumentException {
        validate(book, location);
        this.book = book;
        this.copyNumber = lastCopyNumber++;
        this.location = location;
        this.nextIssuableDate = LocalDate.now();
        this.issuer = null;
        this.reservations = new ArrayDeque<>();
    }

    private void validate(Book book, BookLocation location) throws IllegalArgumentException {
        Validate.objectIsNonNull(book, "Book");
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

    public void issueCopy(Member member, int days) throws BookCantBeIssuedException {
        if (!canBeIssued()) {
            throw new BookCantBeIssuedException("Book Copy already issued");
        }
        issuer = member;
        nextIssuableDate = LocalDate.now().plusDays(days);
    }

    public void returnCopy() throws BookCantBeReturnedException {
        if (canBeIssued()) {
            throw new BookCantBeReturnedException("Book hasn't been issued. Hence cannot be returned.");
        }
        issuer = null;
        nextIssuableDate = LocalDate.now();
    }

    public void addReservationFor(Member member) {
        var newReservation = new Reservation(member, this);
        reservations.add(newReservation);
    }

    public void removeReservationFor(Member member) {
        reservations.removeIf(reservation -> reservation.wasDoneBy(member) && reservation.wasDoneFor(this));
    }

}
