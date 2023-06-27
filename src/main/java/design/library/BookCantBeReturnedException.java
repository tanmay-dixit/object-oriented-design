package design.library;

public class BookCantBeReturnedException extends Exception {

    public BookCantBeReturnedException(String message) {
        super(message);
    }

}
