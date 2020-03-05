package com.booking;

import com.booking.Booking;
import com.booking.BookingController;
import com.booking.BookingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
class BookingControllerTest {
    private BookingRepository bookingRepository;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        bookingRepository = mock(BookingRepository.class);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new BookingController(bookingRepository)).build();
    }

    @Test
    public void greetingsTest() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("greetings from booking. !"));
    }

    @Test
    public void shouldMakeBooking() throws Exception {
        Booking booking = new Booking(1, "uid", "sid");
        when(bookingRepository.save(booking)).thenReturn(booking);

        mockMvc.perform(post("/book")
                .content(bookingReqPayload()).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(new ObjectMapper()
                                .writeValueAsString(booking)));

        verify(bookingRepository).save(booking);
    }

    private String bookingReqPayload() {
        return "{\"numberOfSeats\": \"1\"," +
                "\"userId\": \"uid\", " +
                "\"showId\": \"sid\"}";
    }
}