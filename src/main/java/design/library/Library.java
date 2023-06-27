package design.library;

import java.util.Set;

public class Library {
    private Set<Member> members;
    private Catalog catalog;

    public void addMember(String name) {
        var newMember = new Member(name, catalog);
        members.add(newMember);
    }

    public void renewMembership(Membership membership) {
        membership.renew();
    }

}
