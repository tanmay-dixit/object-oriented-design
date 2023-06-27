package design.library;

import java.time.LocalDate;

public record IssuedItem(
        Member issuer,
        BookCopy issuedCopy,
        LocalDate issueDate,
        LocalDate dueDate,
        LocalDate returnDate
) {
}
