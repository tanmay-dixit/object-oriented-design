package design.library;

public class BookCantBeIssuedException extends Exception {

    public BookCantBeIssuedException(String message) {
        super(message);
    }

}
