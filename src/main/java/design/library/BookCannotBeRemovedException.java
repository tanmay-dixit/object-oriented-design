package design.library;

public class BookCannotBeRemovedException extends Exception {

    public BookCannotBeRemovedException(String message) {
        super(message);
    }

}
