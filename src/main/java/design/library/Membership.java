package design.library;

import java.time.LocalDate;

public class Membership {

    private LocalDate startDate;
    private LocalDate endDate;

    public Membership() {
        this.startDate = LocalDate.now();
        this.endDate = startDate.plusYears(1);
    }

    public void ensureIsActive() throws InactiveMemberException {
        if (!isActive()) {
            throw new InactiveMemberException("Member's membership is not active.");
        }
    }

    public boolean isActive() {
        return endDate.isAfter(LocalDate.now());
    }

    public void renew() {
        if (isActive()) {
            extend();
        } else {
            restart();
        }
    }

    private void restart() {
        startDate = LocalDate.now();
        endDate = startDate.plusYears(1);
    }

    private void extend() {
        endDate = endDate.plusYears(1);
    }
}
