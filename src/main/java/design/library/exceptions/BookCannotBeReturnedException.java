package design.library.exceptions;

public class BookCannotBeReturnedException extends Exception {

    public BookCannotBeReturnedException(String message) {
        super(message);
    }

}
