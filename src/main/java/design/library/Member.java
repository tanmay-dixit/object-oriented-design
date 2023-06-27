package design.library;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Member {

    private final String id;
    private final String name;
    private final Catalog catalog;
    private final Set<BookCopy> issuedCopies;
    private final Set<BookCopy> reservedCopies;
    private final Membership membership;

    public Member(String name, Catalog catalog) {
        Validate.stringIsNotBlank(name, "Member Name");
        Validate.objectIsNonNull(catalog, "Catalog");
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.catalog = catalog;
        this.issuedCopies = new HashSet<>();
        this.reservedCopies = new HashSet<>();
        this.membership = new Membership();
    }

    public String getName() {
        return name;
    }

    public void renewMembership() {
        membership.renew();
    }

    public boolean isActive() {
        return membership.isActive();
    }

    public void issueBook(BookCopy bookCopy, int days) throws IllegalArgumentException, InactiveMemberException, BookCantBeIssuedException {

        membership.ensureIsActive();
        Validate.objectIsNonNull(bookCopy, "Book Copy");
        Validate.intIsPositive(days, "Issue duration");

        if (!bookCopy.canBeIssued()) {
            throw new BookCantBeIssuedException("Book Copy already issued");
        }

        if (reservedCopies.contains(bookCopy)) {
            reservedCopies.remove(bookCopy);
            bookCopy.removeReservationFor(this);
        }

        issuedCopies.add(bookCopy);
        bookCopy.issueCopy(this, days);
    }

    public void returnBook(BookCopy bookCopy) throws IllegalArgumentException, InactiveMemberException, BookCantBeReturnedException {

        membership.ensureIsActive();
        Validate.objectIsNonNull(bookCopy, "Book Copy");

        if (!issuedCopies.contains(bookCopy)) {
            throw new BookCantBeReturnedException("Book copy was not issued by this member");
        }

        issuedCopies.remove(bookCopy);
        bookCopy.returnCopy();
    }

    public void reserveBook(BookCopy bookCopy) throws IllegalArgumentException, InactiveMemberException, BookCantBeReservedException {

        membership.ensureIsActive();
        Validate.objectIsNonNull(bookCopy, "Book Copy");

        if (!bookCopy.canBeReserved()) {
            throw new BookCantBeReservedException("Book Copy cannot be reserved");
        }

        reservedCopies.add(bookCopy);
        bookCopy.addReservationFor(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Member that = (Member) o;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
