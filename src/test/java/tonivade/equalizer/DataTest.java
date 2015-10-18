/*
 * Copyright (c) 2015, Antonio Gabriel Muñoz Conejo <antoniogmc at gmail dot com>
 * Distributed under the terms of the MIT License
 */
package tonivade.equalizer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

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
}
