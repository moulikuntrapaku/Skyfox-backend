package com.booking.bookings.view;

import com.booking.customers.repository.Customer;
import com.booking.shows.respository.Show;
import com.booking.slots.repository.Slot;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.Set;

import static com.booking.shows.respository.Constants.MAX_NO_OF_SEATS_PER_BOOKING;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BookingRequestTest {
    @Test
    public void should_not_allow_seat_booking_for_more_than_maximum() {
        final Show show = new Show(Date.valueOf("2020-11-09"), new Slot("slot 1", Time.valueOf("12:00:00"), Time.valueOf("13:00:00")), BigDecimal.valueOf(299.99), "movie 1");
        final Customer customer = new Customer("customer 1", "992212399");
        int greaterThanMaxSeats = Integer.parseInt(MAX_NO_OF_SEATS_PER_BOOKING) + 1;
        final BookingRequest bookingRequest = new BookingRequest(Date.valueOf("2020-11-06"), show, customer, greaterThanMaxSeats);

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        final Set<ConstraintViolation<BookingRequest>> violations = validator.validate(bookingRequest);

        assertThat(violations.iterator().next().getMessage(), is("Maximum " + MAX_NO_OF_SEATS_PER_BOOKING + " seats allowed per booking"));
    }
}
