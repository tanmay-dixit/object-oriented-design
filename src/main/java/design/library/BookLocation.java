package design.library;

import java.util.Objects;

public class BookLocation {
    private final Subject section;
    private final int shelf;
    private final int position;

    public BookLocation(Subject section, int shelf, int position) {
        this.section = section;
        this.shelf = shelf;
        this.position = position;
    }

    public Subject getSection() {
        return section;
    }

    public int getShelf() {
        return shelf;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookLocation that = (BookLocation) o;
        return shelf == that.shelf && position == that.position && section == that.section;
    }

    @Override
    public int hashCode() {
        return Objects.hash(section, shelf, position);
    }

}
