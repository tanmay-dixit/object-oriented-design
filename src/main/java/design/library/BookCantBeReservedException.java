package design.library;

public class BookCantBeReservedException extends Exception {

    public BookCantBeReservedException(String message) {
        super(message);
    }

}
