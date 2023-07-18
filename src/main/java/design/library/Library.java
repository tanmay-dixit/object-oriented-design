package design.library;

import design.library.exceptions.BookCannotBeIssuedException;
import design.library.exceptions.BookCannotBeReservedException;
import design.library.exceptions.BookCannotBeReturnedException;
import design.library.exceptions.InactiveMemberException;
import design.library.models.*;

import java.time.LocalDate;
import java.util.*;

public class Library {

    private final String name;
    private final Set<Member> members;
    private final Catalog catalog;
    private final Set<Issuance> issueHistory;
    private final Set<Reservation> reservationHistory;

    public Library(String name) {
        this.name = name;
        this.members = new HashSet<>();
        this.catalog = new Catalog();
        this.issueHistory = new LinkedHashSet<>();
        this.reservationHistory = new LinkedHashSet<>();
    }

    public String getName() {
        return name;
    }

    // Member features
    public void issueCopyForMember(BookCopy copy, Member member) throws BookCannotBeIssuedException, InactiveMemberException {
        // Validations
        member.ensureIsActive();
        member.ensureCanIssue();
        copy.ensureCanBeIssued();

        // Logic
        member.addIssuedCopy(copy);
        copy.issueFor(member);
        issueHistory.add(new Issuance(copy, member));
    }

    public void returnCopyForMember(BookCopy copy, Member member) throws BookCannotBeReturnedException, InactiveMemberException {
        // Validations
        member.ensureIsActive();

        // Logic
        member.removeIssuedCopy(copy);
        copy.returnCopy();
        issueHistory.stream()
                .filter(issuance -> issuance.wasDoneBy(member) && issuance.wasDoneFor(copy))
                .findFirst()
                .ifPresent(issuance -> issuance.setReturnDate(LocalDate.now()));
    }

    public void reserveCopyForMember(BookCopy copy, Member member) throws BookCannotBeReservedException, InactiveMemberException {
        // Validations
        member.ensureIsActive();
        copy.ensureCanBeReserved();

        // Logic
        member.addReservedCopy(copy);
        copy.addReservationFor(member);
        reservationHistory.add(new Reservation(copy, member));
    }

    // Search features
    public Set<Book> findBooksByAuthor(String author) {
        return catalog.findBooksByAuthor(author);
    }

    public Set<Book> findBooksByTitle(String title) {
        return catalog.findBooksByTitle(title);
    }

    public Set<Book> findBooksBySubject(Subject subject) {
        return catalog.findBooksBySubject(subject);
    }

    // Librarian features
    public void addMember(String name) {
        var newMember = new Member(name, this);
        members.add(newMember);
    }

    public void renewMember(Member member) {
        member.renewMembership();
    }

    public void removeMember(Member member) {
        members.remove(member);
        member.cancelMembership();
    }

}
