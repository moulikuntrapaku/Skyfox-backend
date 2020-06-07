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
        show = new Show(null, slot, null, "movie_id", movieGateway);
    }

    @Test
    public void should_get_movie_from_gateway() throws IOException, FormatException {
        Movie expectedMovie = new Movie("movie_id", "Movie name", Duration.ofHours(1), "Movie description");
        when(movieGateway.getMovieFromId("movie_id")).thenReturn(expectedMovie);
        ShowResponse showResponse = new ShowResponse(show);

        Movie actualMovie = showResponse.getMovie();

        assertThat(actualMovie, is(equalTo(expectedMovie)));
    }

    @Test
    public void should_get_slot() {
        ShowResponse showResponse = new ShowResponse(show);

        Slot actualSlot = showResponse.getSlot();

        assertThat(actualSlot, is(equalTo(slot)));
    }
}
