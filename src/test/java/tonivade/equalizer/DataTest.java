/*
 * Copyright (c) 2015, Antonio Gabriel Muñoz Conejo <antoniogmc at gmail dot com>
 * Distributed under the terms of the MIT License
 */
package tonivade.equalizer;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static tonivade.comparalizer.Comparalizer.comparalizer;

import java.util.Comparator;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class DataTest {

    private Data data1 = new Data(1, "value");
    private Data data2 = new Data(1, "value");
    private Data data3 = new Data(2, "value");

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Data.class).verify();
    }

    @Test
    public void areEquals() {
        assertThat(data1, equalTo(data2));
    }

    @Test
    public void notEquals() {
        assertThat(data1, not(equalTo(data3)));
    }

    @Test
    public void sameObjects() {
        assertThat(data1, equalTo(data1));
    }

    @Test
    public void differentClasses() {
        assertThat(data1, not(equalTo(new Object())));
    }

    @Test
    public void notEqualsToNull() {
        assertThat(data1, not(equalTo(null)));
    }

    @Test
    public void compareEquals() {
        assertThat(0, equalTo(comparalizer(data1).append(byId())
                .append(byValue()).applyTo(data2)));
    }

    @Test
    public void compareLower() {
        assertThat(-1, equalTo(comparalizer(data1).append(byId())
                .append(byValue()).applyTo(data3)));
    }

    @Test
    public void compareGreater() {
        assertThat(1, equalTo(comparalizer(data3).append(byId())
                .append(byValue()).applyTo(data1)));
    }

    @Test
    public void sort() {
        assertThat(
                asList(data1, data2, data3),
                equalTo(asList(data2, data3, data1).stream()
                        .sorted(byId().thenComparing(byValue()))
                        .collect(toList())));
    }

    private Comparator<Data> byId() {
        return (a, b) -> a.getId() - b.getId();
    }

    private Comparator<Data> byValue() {
        return (a, b) -> a.getValue().compareTo(b.getValue());
    }
}
