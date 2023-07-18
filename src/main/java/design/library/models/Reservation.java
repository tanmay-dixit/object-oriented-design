package design.library.models;

import java.time.LocalDate;

public class Reservation {

    private final Member reserver;
    private final LocalDate reservationDate;
    private final BookCopy copy;

    public Reservation(BookCopy copy, Member reserver) {
        this.copy = copy;
        this.reserver = reserver;
        this.reservationDate = LocalDate.now();
    }

    public boolean wasDoneBy(Member member) {
        return reserver.equals(member);
    }

    public boolean wasDoneFor(BookCopy copy) {
        return this.copy.equals(copy);
    }

}
