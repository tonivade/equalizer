/*
 * Copyright (c) 2015, Antonio Gabriel Muñoz Conejo <antoniogmc at gmail dot com>
 * Distributed under the terms of the MIT License
 */
package tonivade.equalizer;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Equalizer<T> {

    private T target;
    private List<Tester<T>> testers = new LinkedList<>();

    private Equalizer(T target) {
        this.target = requireNonNull(target);
    }

    public Equalizer<T> test(Tester<T> tester) {
        this.testers.add(requireNonNull(tester));
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (isNull(obj)) {
            return false;
        }
        if (sameObjects(obj)) {
            return true;
        }
        if (!sameClasses(obj)) {
            return false;
        }
        return areEquals((T) obj);
    }

    private boolean areEquals(T other) {
        boolean areEquals = false;
        Iterator<Tester<T>> iterator = testers.iterator();
        if (iterator.hasNext()) {
            areEquals = iterator.next().test(target, other);
            while (iterator.hasNext()) {
                areEquals &= iterator.next().test(target, other);
                if (!areEquals) {
                    break;
                }
            }
        }
        return areEquals;
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
        return new Equalizer<T>(target);
    }

    @FunctionalInterface
    public static interface Tester<T> {
        boolean test(T a, T b);
    }
}
