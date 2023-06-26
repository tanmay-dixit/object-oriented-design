package design.library;

import java.time.Instant;

public record ReservedItem(
        Member reserver,
        BookCopy reservedCopy,
        Instant reservedOn
) {
}
