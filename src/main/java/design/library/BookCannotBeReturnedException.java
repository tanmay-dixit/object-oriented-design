package design.library;

public class BookCannotBeReturnedException extends Exception {

    public BookCannotBeReturnedException(String message) {
        super(message);
    }

}
