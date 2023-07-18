package design.library.exceptions;

public class BookCannotBeIssuedException extends Exception {

    public BookCannotBeIssuedException(String message) {
        super(message);
    }

}
