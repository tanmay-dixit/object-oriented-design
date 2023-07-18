package design.library;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Issuance {

    private static final int ISSUE_DURATION_IN_DAYS = 10;
    private static final int FINE_IN_RS_PER_DAY = 5;

    private final BookCopy copy;
    private final Member issuer;
    private final LocalDate issueDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;
    private Fine fine;

    public Issuance(BookCopy copy, Member issuer) {
        this.copy = copy;
        this.issuer = issuer;
        this.issueDate = LocalDate.now();
        this.dueDate = issueDate.plusDays(ISSUE_DURATION_IN_DAYS);
    }

    public boolean wasDoneBy(Member member) {
        return issuer.equals(member);
    }

    public boolean wasDoneFor(BookCopy copy) {
        return this.copy.equals(copy);
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;

        if (isLateReturn()) {
            long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
            int fineAmount = (int) (daysLate * FINE_IN_RS_PER_DAY);
            this.fine = new Fine(this, fineAmount);
        }
    }

    private boolean isLateReturn() {
        return returnDate.isAfter(dueDate);
    }

    public Fine getFine() {
        return fine;
    }

}
