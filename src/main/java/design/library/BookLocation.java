package design.library;

public record BookLocation(
        Category category,

        int rack,
        int shelf,
        int position
) {
}
