package com.booking.shows;

import com.booking.movieGateway.MovieGateway;
import com.booking.shows.respository.Show;
import com.booking.shows.respository.ShowRepository;
import com.booking.slots.repository.Slot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShowServiceTest {
    private ShowRepository showRepository;
    private MovieGateway movieGateway;

    @BeforeEach
    public void beforeEach() {
        showRepository = mock(ShowRepository.class);
        movieGateway = mock(MovieGateway.class);
    }

    @Test
    public void should_fetch_all_shows_and_set_movie_gateway() {
        List<Show> shows = new ArrayList<>();
        Slot slotOne = new Slot();
        Slot slotTwo = new Slot();

        shows.add(new Show(
                1L,
                Date.valueOf("2020-01-01"),
                slotOne,
                new BigDecimal("299.99"),
                "movie_1",
                null));

        shows.add(new Show(
                1L,
                Date.valueOf("2020-01-01"),
                slotTwo,
                new BigDecimal("299.99"),
                "movie_1",
                null));

        when(showRepository.findAll()).thenReturn(shows);
        ShowService showService = new ShowService(showRepository, movieGateway);

        List<Show> actualShows = showService.fetchAll();

        List<Show> expectedShows = new ArrayList<>();
        expectedShows.add(new Show(
                1L,
                Date.valueOf("2020-01-01"),
                slotOne,
                new BigDecimal("299.99"),
                "movie_1",
                movieGateway));

        expectedShows.add(new Show(
                1L,
                Date.valueOf("2020-01-01"),
                slotTwo,
                new BigDecimal("299.99"),
                "movie_1",
                movieGateway));

        assertThat(actualShows, is(equalTo(expectedShows)));
    }
}
