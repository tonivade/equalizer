package tonivade.comparalizer;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

public class Comparalizer<T> {
    private T target;
    private Comparator<T> comparator = (a, b) -> 0;

    private Comparalizer(T target) {
        this.target = requireNonNull(target);
    }

    public Comparalizer<T> append(Comparator<T> appended) {
        comparator = comparator.thenComparing(appended);
        return this;
    }

    public int applyTo(T other) {
        return comparator.compare(target, other);
    }

    public static <T> Comparalizer<T> comparalizer(T target) {
        return new Comparalizer<>(target);
    }
}
