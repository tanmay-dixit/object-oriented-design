package design.library;

import java.util.Set;

public record Library(
        Set<Member> members,
        Catalog catalog
) {
}
