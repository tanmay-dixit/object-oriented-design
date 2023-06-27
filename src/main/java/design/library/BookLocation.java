package design.library;

public class BookLocation {
    private final Category section;
    private final int rack;
    private final int shelf;
    private final int position;

    public BookLocation(Category section, int rack, int shelf, int position) throws IllegalArgumentException {
        validate(section, rack, shelf, position);
        this.section = section;
        this.rack = rack;
        this.shelf = shelf;
        this.position = position;
    }

    private void validate(Category section, int rack, int shelf, int position) throws IllegalArgumentException {
        Validate.objectIsNonNull(section, "Section");
        Validate.intIsPositive(rack, "Rack");
        Validate.intIsPositive(shelf, "Shelf");
        Validate.intIsPositive(position, "Position");
    }

    public Category getSection() {
        return section;
    }

    public int getRack() {
        return rack;
    }

    public int getShelf() {
        return shelf;
    }

    public int getPosition() {
        return position;
    }

}
