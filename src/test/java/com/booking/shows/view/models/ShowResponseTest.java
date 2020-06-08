package com.booking.shows.view.models;

import com.booking.movieGateway.MovieGateway;
import com.booking.movieGateway.exceptions.FormatException;
import com.booking.movieGateway.models.Movie;
import com.booking.shows.respository.Show;
import com.booking.slots.repository.Slot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Time;
import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ShowResponseTest {

    private MovieGateway movieGateway;
    private Slot slot;
    private Show show;

    @BeforeEach
    public void beforeEach() {
        movieGateway = mock(MovieGateway.class);
        slot = new Slot("Slot name", Time.valueOf("13:30:00"), Time.valueOf("16:00:00"));
        show = new Show(null, slot, null, "movie_id");
    }

    @Test
    public void should_get_movie_from_gateway() throws IOException, FormatException {
        Movie movie = mock(Movie.class);
        ShowResponse showResponse = new ShowResponse(movie, slot, show);

        Movie actualMovie = showResponse.getMovie();

        assertThat(actualMovie, is(equalTo(movie)));
    }

    @Test
    public void should_get_slot() {
        ShowResponse showResponse = new ShowResponse(mock(Movie.class), slot, show);

        Slot actualSlot = showResponse.getSlot();

        assertThat(actualSlot, is(equalTo(slot)));
    }
}
