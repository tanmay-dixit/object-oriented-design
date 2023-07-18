package design.library.exceptions;

public class BookCannotBeReservedException extends Exception {

    public BookCannotBeReservedException(String message) {
        super(message);
    }

}
