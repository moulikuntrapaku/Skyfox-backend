package com.booking.shows;

import com.booking.App;
import com.booking.shows.Show;
import com.booking.shows.ShowController;
import com.booking.shows.ShowRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = App.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ShowControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void greetingsTest() throws Exception {
        mockMvc.perform(get("/shows"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("greetings from booking. !"));
    }

    @Test
    public void shouldMakeBooking() throws Exception {

        mockMvc.perform(post("/shows")
                .content(bookingReqPayload()).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("name_1"))
                .andExpect(jsonPath("$.price").value("34.1"))
                .andExpect(jsonPath("$.description").value("desc_1"));

    }

    private String bookingReqPayload() {
        return "{\"name\": \"name_1\"," +
                "\"description\": \"desc_1\", " +
                "\"price\": 34.1}";
    }
}