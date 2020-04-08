package com.booking.shows;

import com.booking.App;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Arrays.asList;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = App.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
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

        mockMvc.perform(post("/shows")
            .content(addShowRequestPayload()).contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("name_1"))
            .andExpect(jsonPath("$.price").value("34.1"))
            .andExpect(jsonPath("$.description").value("desc_1"))
            .andExpect(jsonPath("$.status").value("RUNNING"));

    }

    private String addShowRequestPayload() {
        return "{\"name\": \"name_1\"," +
            "\"description\": \"desc_1\", " +
            "\"price\": 34.1," +
            "\"status\":\"RUNNING\"}";
    }
}
