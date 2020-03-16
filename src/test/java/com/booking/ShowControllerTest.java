package com.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ShowControllerTest {
    private ShowRepository showRepository;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        showRepository = mock(ShowRepository.class);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new ShowController(showRepository)).build();
    }

    @Test
    public void greetingsTest() throws Exception {
        mockMvc.perform(get("/shows"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("greetings from booking. !"));
    }

    @Test
    public void shouldMakeBooking() throws Exception {
        Show show = new Show(1L, "name_1", "desc_1", 34.1);
        when(showRepository.save(show)).thenReturn(show);

        mockMvc.perform(post("/book")
                .content(bookingReqPayload()).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(new ObjectMapper()
                                .writeValueAsString(show)));

        verify(showRepository).save(show);
    }

    private String bookingReqPayload() {
        return "{\"numberOfSeats\": \"1\"," +
                "\"userId\": \"uid\", " +
                "\"showId\": \"sid\"}";
    }
}