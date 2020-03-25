package com.booking;

import org.junit.jupiter.api.Test;

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
}
