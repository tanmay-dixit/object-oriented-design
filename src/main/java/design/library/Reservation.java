package design.library;

import java.time.Instant;
import java.time.LocalDate;

public class Reservation {

    private final Member reserver;
    private final LocalDate reservationDate;
    private final LocalDate availableDate;
    private final BookCopy copy;

    public Reservation(Member reserver,
                       LocalDate availableDate,
                       BookCopy copy) {
            this.reserver = reserver;
            this.reservationDate = LocalDate.now();
            this.availableDate = availableDate;
            this.copy = copy;
    }

}
