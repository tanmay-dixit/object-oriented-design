package design.library.models;

import design.library.Library;
import design.library.exceptions.BookCannotBeIssuedException;
import design.library.exceptions.BookCannotBeReservedException;
import design.library.exceptions.BookCannotBeReturnedException;
import design.library.exceptions.InactiveMemberException;

import java.util.*;

public class Member {

    private static final int MAX_ISSUANCES = 5;

    private final String id;
    private final String name;
    private final Library library;
    private final Set<BookCopy> issuedCopies;
    private final Set<BookCopy> reservedCopies;
    private final Membership membership;

    public Member(String name, Library library) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.library = library;
        this.issuedCopies = new HashSet<>();
        this.reservedCopies = new HashSet<>();
        this.membership = new Membership(this);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Library getLibrary() {
        return library;
    }

    public Set<BookCopy> getIssuedCopies() {
        return issuedCopies;
    }

    public Set<BookCopy> getReservedCopies() {
        return reservedCopies;
    }

    public void renewMembership() {
        membership.renew();
    }

    public void cancelMembership() {
        membership.cancel();
    }

    public void ensureCanIssue() throws BookCannotBeIssuedException {
        if(issuedCopies.size() == MAX_ISSUANCES) {
            throw new BookCannotBeIssuedException("Maximum book issuance reached for member");
        }
    }

    public void ensureIsActive() throws InactiveMemberException {
        membership.ensureIsActive();
    }

    public void issueCopy(BookCopy copy) throws BookCannotBeIssuedException, InactiveMemberException {
        library.issueCopyForMember(copy, this);
    }

    public void addIssuedCopy(BookCopy copy) {
        this.issuedCopies.add(copy);
    }

    public void returnCopy(BookCopy copy) throws BookCannotBeReturnedException, InactiveMemberException {
        library.returnCopyForMember(copy, this);
    }

    public void removeIssuedCopy(BookCopy copy) {
        this.issuedCopies.remove(copy);
    }

    public void reserveCopy(BookCopy copy) throws BookCannotBeReservedException, InactiveMemberException {
        library.reserveCopyForMember(copy, this);
    }

    public void addReservedCopy(BookCopy copy) {
        this.reservedCopies.add(copy);
    }

    public Set<Book> findBooksByAuthor(String author) {
        return library.findBooksByAuthor(author);
    }

    public Set<Book> findBooksByTitle(String title) {
        return library.findBooksByTitle(title);
    }

    public Set<Book> findBooksBySubject(Subject subject) {
        return library.findBooksBySubject(subject);
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
