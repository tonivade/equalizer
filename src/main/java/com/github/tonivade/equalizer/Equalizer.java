/*
 * Copyright (c) 2015, Antonio Gabriel Muñoz Conejo <antoniogmc at gmail dot com>
 * Distributed under the terms of the MIT License
 */
package com.github.tonivade.equalizer;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class Equalizer<T> {

  private T target;
  private List<Matcher<T>> matchers = new LinkedList<>();

  private Equalizer(T target) {
    this.target = requireNonNull(target);
  }

  public Equalizer<T> append(Matcher<T> matcher) {
    this.matchers.add(requireNonNull(matcher));
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
    return sameClasses(obj) && matches((T) obj);
  }

  private boolean sameObjects(Object obj) {
    return target == obj;
  }

  private boolean sameClasses(Object obj) {
    return target.getClass() == obj.getClass();
  }

  private boolean matches(T other) {
    return matchers.stream().allMatch(matcher -> matcher.match(target, other));
  }

  public static <T> Equalizer<T> equalizer(T target) {
    return new Equalizer<>(target);
  }

  @FunctionalInterface
  public interface Matcher<T> {
    boolean match(T a, T b);
  }

  public static <T, V> Matcher<T> comparing(Function<T, V> getter) {
    return (a, b) -> Objects.equals(getter.apply(a), getter.apply(b));
  }
}
