package com.booking.bookings;

import com.booking.bookings.repository.Booking;
import com.booking.bookings.repository.BookingRepository;
import com.booking.customers.repository.Customer;
import com.booking.customers.repository.CustomerRepository;
import com.booking.shows.respository.Show;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;

    public BookingService(BookingRepository bookingRepository, CustomerRepository customerRepository) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
    }

    public void book(Customer customer, Show show, Date bookingDate, int noOfSeats) {
        customerRepository.save(customer);
        BigDecimal amountPaid = show.costFor(noOfSeats);
        bookingRepository.save(new Booking(bookingDate, show, customer, noOfSeats, amountPaid));
    }
}
