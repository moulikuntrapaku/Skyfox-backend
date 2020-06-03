package com.booking.bookings;

import com.booking.bookings.repository.Booking;
import com.booking.bookings.repository.BookingRepository;
import com.booking.customers.repository.Customer;
import com.booking.customers.repository.CustomerRepository;
import com.booking.movieGateway.MovieGateway;
import com.booking.shows.respository.Show;
import com.booking.slots.repository.Slot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

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
        Slot slot = new Slot(1, "13:00-16:00", Time.valueOf("13:00:00"), Time.valueOf("16:00:00"));
        show = new Show(Long.MIN_VALUE, bookingDate, slot, BigDecimal.valueOf(250), "1", mock(MovieGateway.class));
        customer = new Customer("Customer name", "9090909090");
        bookingService = new BookingService(bookingRepository, customerRepository);
    }

    @Test
    public void should_save_booking() {
        int noOfSeats = 2;
        Booking booking = new Booking(bookingDate, show, customer, noOfSeats, BigDecimal.valueOf(500));

        bookingService.book(customer, show, bookingDate, noOfSeats);

        verify(bookingRepository).save(booking);
    }

    @Test
    public void should_save_customer_who_requests_booking() {
        bookingService.book(customer, show, bookingDate, 2);

        verify(customerRepository).save(customer);
    }
}
