package design.library;

public class BookCantBeRemovedException extends Exception {

    public BookCantBeRemovedException(String message) {
        super(message);
    }

}
