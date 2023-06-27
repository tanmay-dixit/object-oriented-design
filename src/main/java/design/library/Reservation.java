package design.library;

import java.time.LocalDate;

public class Reservation {

    private final Member reserver;
    private final LocalDate reservationDate;
    private final BookCopy copy;

    public Reservation(Member reserver,
                       BookCopy copy) {
        this.reserver = reserver;
        this.reservationDate = LocalDate.now();
        this.copy = copy;
    }

    public boolean wasDoneBy(Member member) {
        return reserver.equals(member);
    }

    public boolean wasDoneFor(BookCopy copy) {
        return this.copy.equals(copy);
    }

}
