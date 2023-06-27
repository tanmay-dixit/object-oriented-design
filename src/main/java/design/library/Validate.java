package design.library;

import java.util.Objects;

public class Validate {

    private Validate() {
    }

    public static void objectIsNonNull(Object o, String name) throws IllegalArgumentException {
        Objects.requireNonNull(o, name + " cannot be null");
    }

    public static void stringIsNotBlank(String s, String name) throws IllegalArgumentException {
        Validate.objectIsNonNull(s, name);
        if (s.isBlank()) {
            throw new IllegalArgumentException(name + " cannot be blank");
        }
    }

    public static void intIsPositive(int n, String name) throws IllegalArgumentException {
        if (n <= 0) {
            throw new IllegalArgumentException(name + " needs to be positive");
        }
    }


}
