package com.booking.bookings.view;

import com.booking.bookings.BookingService;
import com.booking.customers.repository.Customer;
import com.booking.exceptions.NoSeatAvailableException;
import com.booking.handlers.models.ErrorResponse;
import com.booking.shows.respository.Show;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;

@Api(tags = "Bookings")
@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    @ApiOperation(value = "Create a booking")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created a booking successfully"),
            @ApiResponse(code = 500, message = "Something failed in the server", response = ErrorResponse.class)
    })
    public void book(@Valid @RequestAttribute(name = "date") Date date, @Valid @RequestAttribute(name = "show") Show show, @Valid @RequestAttribute(name = "customer") Customer customer, @Valid @RequestAttribute(name = "noOfSeats") int noOfSeats) throws NoSeatAvailableException {
        bookingService.book(customer, show, date, noOfSeats);
    }
}
