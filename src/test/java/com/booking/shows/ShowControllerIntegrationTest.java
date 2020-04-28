package com.booking.shows;

import com.booking.App;
import com.booking.users.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.List;

import static com.booking.shows.helpers.ShowApiHelper.isEmptyBody;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = App.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@WithMockUser
class ShowControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShowRepository showRepository;

    @BeforeEach
    public void before() {
        showRepository.deleteAll();
    }

    @AfterEach
    public void after() {
        showRepository.deleteAll();
    }

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void retrieveAllExistingShows() throws Exception {
        Show show1 = new Show("hello", "world", 7, ShowStatus.RUNNING);
        Show show2 = new Show("abc", "dummy description", 1, ShowStatus.RUNNING);
        final var shows = asList(show1, show2);
        showRepository.saveAll(shows);
        mockMvc.perform(get("/shows"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[0].name").value("hello"))
                .andExpect(jsonPath("$[1].description").value("dummy description"))
                .andExpect(jsonPath("$[1].status").value("RUNNING"));
    }

    @Test
    public void makeAPostRequestToSaveAShow() throws Exception {
        Show newShow = new Show("name_1", "desc_1", 34.1, ShowStatus.RUNNING);

        mockMvc.perform(post("/shows")
                .content(mapToJson(newShow)).contentType(APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("name_1"))
                .andExpect(jsonPath("$.price").value("34.1"))
                .andExpect(jsonPath("$.description").value("desc_1"))
                .andExpect(jsonPath("$.status").value("RUNNING"));
    }

    @Test
    public void makeADeleteRequestToDeleteTheShow() throws Exception {
        Show show1 = new Show("Movie 1", "Description 1", 100, ShowStatus.RUNNING);
        Show show2 = new Show("Movie 2", "Description 2", 200, ShowStatus.UPCOMING);

        Show savedShow1 = showRepository.save(show1);
        Show savedShow2 = showRepository.save(show2);

        mockMvc.perform(delete("/shows/" + savedShow1.getId())
                .content("").contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(isEmptyBody());

        List<Show> actualAllShows = showRepository.findAll();
        assertThat(actualAllShows.size(), is(equalTo(1)));
        assertThat(actualAllShows.get(0), is(equalTo(savedShow2)));
    }

    @Test
    public void makeAnUpdateRequestToAShow() throws Exception {
        Show show1 = new Show("Movie 1", "Description 1", 100, ShowStatus.RUNNING);

        Show savedShow1 = showRepository.save(show1);
        Long showId = savedShow1.getId();
        Show updatedShow = new Show("Movie 1 updated", "Description 1 updated", 150, ShowStatus.UPCOMING);

        mockMvc.perform(put("/shows/" + showId)
                .content(mapToJson(updatedShow)).contentType(APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(showId))
                .andExpect(jsonPath("$.name").value("Movie 1 updated"))
                .andExpect(jsonPath("$.description").value("Description 1 updated"))
                .andExpect(jsonPath("$.price").value("150.0"))
                .andExpect(jsonPath("$.status").value("UPCOMING"));

        @SuppressWarnings("OptionalGetWithoutIsPresent")
        Show actualShow = showRepository.findById(showId).get().withId(showId);
        assertThat(actualShow, is(equalTo(updatedShow.withId(showId))));
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }
}
