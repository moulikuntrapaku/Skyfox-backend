package com.booking.bookings;

import com.booking.bookings.repository.Booking;
import com.booking.bookings.repository.BookingRepository;
import com.booking.customers.repository.Customer;
import com.booking.customers.repository.CustomerRepository;
import com.booking.exceptions.NoSeatAvailableException;
import com.booking.shows.respository.Show;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;

import static com.booking.shows.respository.Constants.TOTAL_NO_OF_SEATS;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;

    public BookingService(BookingRepository bookingRepository, CustomerRepository customerRepository) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
    }

    public void book(Customer customer, Show show, Date bookingDate, int noOfSeats) throws NoSeatAvailableException {
        if (noOfSeats > TOTAL_NO_OF_SEATS || availableSeats(show) < noOfSeats) {
            throw new NoSeatAvailableException("No seats available");
        }
        customerRepository.save(customer);
        BigDecimal amountPaid = show.costFor(noOfSeats);
        bookingRepository.save(new Booking(bookingDate, show, customer, noOfSeats, amountPaid));
    }

    private long availableSeats(Show show) {
        return TOTAL_NO_OF_SEATS - bookingRepository.bookedSeatsByShow(show.getId());
    }
}
