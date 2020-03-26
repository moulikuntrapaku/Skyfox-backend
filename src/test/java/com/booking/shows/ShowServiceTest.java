package com.booking.shows;

import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShowServiceTest {
    @Test
    public void shouldSaveTheShowToTheRepositoryAndReturnTheSavedShow(){
        final Show originalShow = new Show("movie_show", "some_desc", 299);
        ShowRepository repository = mock(ShowRepository.class);
        Show savedShow = new Show();
        when(repository.save(originalShow)).thenReturn(savedShow);
        ShowService showService = new ShowService(repository);

        assertEquals(savedShow, showService.save(originalShow));
    }

    @Test
    public void shouldRetrieveAllShows(){
        ShowRepository showRepository = mock(ShowRepository.class);
        final var showList = asList(new Show("1", "1", 1), new Show("2", "2", 2));
        when(showRepository.findAll()).thenReturn(showList);

        ShowService showService = new ShowService(showRepository);

        assertEquals(showList, showService.fetchAll());
    }
}
