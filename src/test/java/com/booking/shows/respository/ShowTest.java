package com.booking.shows.respository;

import com.booking.movieGateway.MovieGateway;
import com.booking.slots.repository.Slot;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.hamcrest.MatcherAssert.assertThat;


public class ShowTest {
    @Test
    public void should_return_cost_for_seats() {
        final Slot slot = new Slot("3pm-5pm", Time.valueOf("15:00:00"), Time.valueOf("17:00:00"));
        final Show show = new Show(Date.valueOf("2020-12-01"), slot, BigDecimal.valueOf(200), "movieID", mock(MovieGateway.class));

        final BigDecimal totalCostForSeats = show.costFor(3);

        assertThat(totalCostForSeats, closeTo(BigDecimal.valueOf(600), BigDecimal.ZERO));
    }
}
