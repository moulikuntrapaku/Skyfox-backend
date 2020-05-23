package com.booking.shows.view;

import com.booking.App;
import com.booking.movieGateway.MovieGateway;
import com.booking.movieGateway.models.Movie;
import com.booking.movieGateway.models.MovieStatus;
import com.booking.shows.respository.Show;
import com.booking.shows.respository.ShowRepository;
import com.booking.slots.repository.Slot;
import com.booking.slots.repository.SlotRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = App.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@WithMockUser
public class ShowControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private SlotRepository slotRepository;

    @MockBean
    private MovieGateway movieGateway;

    @BeforeEach
    public void before() {
        showRepository.deleteAll();
        slotRepository.deleteAll();
    }

    @AfterEach
    public void after() {
        showRepository.deleteAll();
        slotRepository.deleteAll();
    }

    @Test
    public void retrieveAllExistingShows() throws Exception {
        when(movieGateway.getMovieFromId("movie_1"))
                .thenReturn(
                        new Movie(
                                "movie_1",
                                "Movie name",
                                Duration.ofHours(1).plusMinutes(30),
                                "Movie description",
                                MovieStatus.RUNNING
                        )
                );
        Slot slotOne = new Slot(1, "Test slot one", Time.valueOf("09:30:00"), Time.valueOf("12:00:00"));
        Slot slotTwo = new Slot(2, "Test slot two", Time.valueOf("13:30:00"), Time.valueOf("16:00:00"));
        Show showOne = new Show(1L, Date.valueOf("2020-01-01"), slotOne, new BigDecimal("249.99"), "movie_1", movieGateway);
        Show showTwo = new Show(2L, Date.valueOf("2020-01-01"), slotTwo, new BigDecimal("299.99"), "movie_1", movieGateway);
        Show showThree = new Show(3L, Date.valueOf("2020-01-02"), slotOne, new BigDecimal("249.99"), "movie_1", movieGateway);
        List<Show> shows = asList(showOne, showTwo, showThree);
        slotRepository.save(slotOne);
        slotRepository.save(slotTwo);
        showRepository.saveAll(shows);

        mockMvc.perform(get("/shows?date=2020-01-01"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[" +
                                "{'id':1,'date':'2020-01-01','cost':249.99," +
                                "'slot':{'id':1,'name':'Test slot one','startTime':'9:30 AM','endTime':'12:00 PM'}," +
                                "'movie':{'id':'movie_1','name':'Movie name','duration':'1h 30m','description':'Movie description','status':'RUNNING'}}," +
                                "{'id':2,'date':'2020-01-01','cost':299.99," +
                                "'slot':{'id':2,'name':'Test slot two','startTime':'1:30 PM','endTime':'4:00 PM'}," +
                                "'movie':{'id':'movie_1','name':'Movie name','duration':'1h 30m','description':'Movie description','status':'RUNNING'}}" +
                                "]"));
    }
}
