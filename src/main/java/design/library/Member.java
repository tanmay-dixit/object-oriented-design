package design.library;

import java.time.Instant;
import java.util.List;

public record Member(
        String name,
        List<BookCopy> currentlyIssued,
        List<BookCopy> currentlyReserved,
        Instant membershipStart,
        Instant membershipEnd
) {

}
