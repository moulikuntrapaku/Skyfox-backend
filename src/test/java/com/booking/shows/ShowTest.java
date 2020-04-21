package com.booking.shows;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ShowTest {

    @Test
    public void shouldReturnAShowWithGivenId() {
        Show show = new Show("Show name", "Show description", 100, ShowStatus.RUNNING);

        assertThat(show.withId(5).getId(), is(equalTo(5L)));
    }
}
