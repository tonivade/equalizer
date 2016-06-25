/*
 * Copyright (c) 2015, Antonio Gabriel Muñoz Conejo <antoniogmc at gmail dot com>
 * Distributed under the terms of the MIT License
 */
package tonivade.equalizer;

import static java.util.Objects.requireNonNull;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;

public class Equalizer<T> {

    private T target;
    private List<Tester<T>> testers = new LinkedList<>();

    private Equalizer(T target) {
        this.target = requireNonNull(target);
    }

    public Equalizer<T> append(Tester<T> tester) {
        this.testers.add(requireNonNull(tester));
        return this;
    }

    @SuppressWarnings("unchecked")
    public boolean applyTo(Object obj) {
        if (isNull(obj)) {
            return false;
        }
        if (sameObjects(obj)) {
            return true;
        }
        return sameClasses(obj) && areEquals((T) obj);
    }

    private boolean areEquals(T other) {
        return testers.stream().allMatch(t -> t.apply(target, other));
    }

    private boolean isNull(Object obj) {
        return obj == null;
    }

    private boolean sameClasses(Object obj) {
        return target.getClass() == obj.getClass();
    }

    private boolean sameObjects(Object obj) {
        return target == obj;
    }

    public static <T> Equalizer<T> equalizer(T target) {
        return new Equalizer<>(target);
    }

    @FunctionalInterface
    public interface Tester<T> extends BiFunction<T, T, Boolean> {
    }
}
