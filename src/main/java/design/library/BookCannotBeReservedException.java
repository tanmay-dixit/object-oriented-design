package design.library;

public class BookCannotBeReservedException extends Exception {

    public BookCannotBeReservedException(String message) {
        super(message);
    }

}
