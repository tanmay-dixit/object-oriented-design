package design.library.exceptions;

public class BookCannotBeRemovedException extends Exception {

    public BookCannotBeRemovedException(String message) {
        super(message);
    }

}
