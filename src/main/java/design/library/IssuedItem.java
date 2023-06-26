package design.library;

import java.time.Instant;

public record IssuedItem(
        Member issuer,
        BookCopy issuedCopy,
        Instant issueDate,
        Instant dueDate,
        Instant returnDate
) {
}
