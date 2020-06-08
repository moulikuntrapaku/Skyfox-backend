package com.booking.bookings;

import com.booking.bookings.repository.Booking;
import com.booking.bookings.repository.BookingRepository;
import com.booking.customers.repository.Customer;
import com.booking.customers.repository.CustomerRepository;
import com.booking.exceptions.NoSeatAvailableException;
import com.booking.shows.respository.Show;
import com.booking.slots.repository.Slot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

import static com.booking.shows.respository.Constants.TOTAL_NO_OF_SEATS;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BookingServiceTest {
    private BookingRepository bookingRepository;
    private BookingService bookingService;
    private Date bookingDate;
    private Show show;
    private Customer customer;
    private CustomerRepository customerRepository;

    @BeforeEach
    public void beforeEach() {
        bookingRepository = mock(BookingRepository.class);
        customerRepository = mock(CustomerRepository.class);
        bookingDate = Date.valueOf("2020-06-01");
        Slot slot = new Slot("13:00-16:00", Time.valueOf("13:00:00"), Time.valueOf("16:00:00"));
        show = new Show(bookingDate, slot, BigDecimal.valueOf(250), "1");
        customer = new Customer("Customer name", "9090909090");
        bookingService = new BookingService(bookingRepository, customerRepository);
    }

    @Test
    public void should_save_booking() throws NoSeatAvailableException {
        int noOfSeats = 2;
        Booking booking = new Booking(bookingDate, show, customer, noOfSeats, BigDecimal.valueOf(500));

        bookingService.book(customer, show, bookingDate, noOfSeats);

        verify(bookingRepository).save(booking);
    }

    @Test
    public void should_save_customer_who_requests_booking() throws NoSeatAvailableException {
        bookingService.book(customer, show, bookingDate, 2);

        verify(customerRepository).save(customer);
    }

    @Test
    public void should_not_book_seat_when_no_of_seats_exceeds_total_seats() {
        assertThrows(NoSeatAvailableException.class, () -> bookingService.book(customer, show, bookingDate, TOTAL_NO_OF_SEATS + 1));
        verifyZeroInteractions(customerRepository);
        verify(bookingRepository, never()).save(any(Booking.class));
    }

    @Test
    public void should_not_book_seat_when_seats_are_not_available() {
        when(bookingRepository.bookedSeatsByShow(show.getId())).thenReturn(100);
        assertThrows(NoSeatAvailableException.class, () -> bookingService.book(customer, show, bookingDate, 2));
        verifyZeroInteractions(customerRepository);
        verify(bookingRepository, never()).save(any(Booking.class));
    }
}
