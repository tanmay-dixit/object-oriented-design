package design.library;

public class BookCannotBeIssuedException extends Exception {

    public BookCannotBeIssuedException(String message) {
        super(message);
    }

}
