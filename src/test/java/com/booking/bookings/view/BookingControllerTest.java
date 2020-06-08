package com.booking.bookings.view;

import com.booking.bookings.BookingService;
import com.booking.customers.repository.Customer;
import com.booking.exceptions.NoSeatAvailableException;
import com.booking.movieGateway.MovieGateway;
import com.booking.shows.respository.Show;
import com.booking.slots.repository.Slot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BookingControllerTest {
    private BookingService bookingService;

    @BeforeEach
    public void beforeEach() {
        bookingService = mock(BookingService.class);
    }

    @Test
    public void should_create_booking_for_customer() throws NoSeatAvailableException {
        final BookingController bookingController = new BookingController(bookingService);
        final Date bookingDate = Date.valueOf("2020-06-01");
        final Slot slot = new Slot("13:00-16:00", Time.valueOf("13:00:00"), Time.valueOf("16:00:00"));
        final Show show = new Show(bookingDate, slot, BigDecimal.valueOf(250), "1");
        final Customer customer = new Customer("Customer name", "9090909090");
        int noOfSeats = 2;

        bookingController.book(bookingDate, show, customer, noOfSeats);

        verify(bookingService).book(customer, show, bookingDate, noOfSeats);
    }
}
