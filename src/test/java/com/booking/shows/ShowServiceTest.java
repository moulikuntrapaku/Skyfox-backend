package com.booking.shows;

import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import static com.booking.shows.ShowStatus.RUNNING;
import static com.booking.shows.ShowStatus.UPCOMING;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ShowServiceTest {
    @Test
    public void shouldSaveTheShowToTheRepositoryAndReturnTheSavedShow() {
        final Show originalShow = new Show("movie_show", "some_desc", 299, RUNNING);
        ShowRepository repository = mock(ShowRepository.class);
        Show savedShow = new Show();
        when(repository.save(originalShow)).thenReturn(savedShow);
        ShowService showService = new ShowService(repository);

        assertThat(showService.save(originalShow), is(equalTo(savedShow)));
    }

    @Test
    public void shouldRetrieveAllShows() {
        ShowRepository showRepository = mock(ShowRepository.class);
        final var showList = asList(new Show("1", "1", 1, RUNNING),
                new Show("2", "2", 2, RUNNING));
        when(showRepository.findAll()).thenReturn(showList);

        ShowService showService = new ShowService(showRepository);

        assertThat(showService.fetchAll(), is(equalTo(showList)));
    }

    @Test
    public void shouldDeleteShowById() {
        ShowRepository showRepository = mock(ShowRepository.class);
        ShowService showService = new ShowService(showRepository);
        showService.delete(5L);

        verify(showRepository, times(1)).deleteById(5L);
    }

    @Test
    public void shouldNotAllowToUpdateIfIdNotPresent() {
        ShowRepository showRepository = mock(ShowRepository.class);
        ShowService showService = new ShowService(showRepository);
        when(showRepository.existsById(5L)).thenReturn(false);
        Show dummyShow = new Show();

        EmptyResultDataAccessException exception =
                assertThrows(EmptyResultDataAccessException.class, () -> showService.update(5L, dummyShow));
        assertThat(exception.getMessage(), is(equalTo("Show with id=5 not found")));
    }

    @Test
    public void shouldUpdateIfIdPresent() {
        ShowRepository showRepository = mock(ShowRepository.class);
        ShowService showService = new ShowService(showRepository);
        when(showRepository.existsById(5L)).thenReturn(true);
        Show updatedShow = new Show("Update name", "Updated description", 100, UPCOMING);
        Show updatedShowWithId = updatedShow.withId(5);
        when(showRepository.save(updatedShowWithId)).thenReturn(updatedShowWithId);

        assertThat(showService.update(5L, updatedShow), is(equalTo(updatedShow)));
    }
}
