package design.library;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

public class BookCopy {

    private static final int MAX_RESERVATIONS = 3;
    private static int lastCopyNumber = 1;
    private final Book book;
    private final int copyNumber;
    private BookLocation location;
    private Issuance issuance;
    private final Queue<Reservation> reservations;

    public BookCopy(Book book, BookLocation location) {
        this.book = book;
        this.copyNumber = lastCopyNumber++;
        this.location = location;
        this.issuance = null;
        this.reservations = new ArrayDeque<>();
    }

    public int getCopyNumber() {
        return copyNumber;
    }

    public BookLocation getLocation() {
        return location;
    }


    public boolean canBeIssued() {
        return issuance == null;
    }

    public boolean canBeReserved() {
        return !canBeIssued() && reservations.size() < MAX_RESERVATIONS;
    }

    public void updateLocation(BookLocation newLocation) {
        location = newLocation;
    }

    public void issueFor(Member member) throws BookCannotBeIssuedException {
        if (!canBeIssued()) {
            throw new BookCannotBeIssuedException("Book Copy already issued");
        }
        this.issuance = new Issuance(this, member);
    }

    public void returnCopy() throws BookCannotBeReturnedException {
        if (canBeIssued()) {
            throw new BookCannotBeReturnedException("Book hasn't been issued. Hence cannot be returned.");
        }
        this.issuance = null;
    }

    public void addReservationFor(Member member) {
        var newReservation = new Reservation(this, member);
        this.reservations.add(newReservation);
    }

    public void removeReservationFor(Member member) {
        reservations.removeIf(reservation -> reservation.wasDoneBy(member) && reservation.wasDoneFor(this));
    }

    public void ensureCanBeIssued() throws BookCannotBeIssuedException {
        if (!canBeIssued()) {
            throw new BookCannotBeIssuedException("Book Copy already issued");
        }
    }


    public void ensureCanBeReserved() throws BookCannotBeReservedException {
        if (!canBeReserved()) {
            throw new BookCannotBeReservedException("Book Copy cannot be reserved");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookCopy copy = (BookCopy) o;
        return copyNumber == copy.copyNumber && Objects.equals(book, copy.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, copyNumber);
    }
}
