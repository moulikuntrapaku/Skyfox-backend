package com.booking.bookings.view;

import com.booking.customers.repository.Customer;
import com.booking.shows.respository.Show;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.DecimalMax;
import java.sql.Date;

import static com.booking.shows.respository.Constants.MAX_NO_OF_SEATS_PER_BOOKING;

public class BookingRequest {
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @ApiModelProperty(name = "date", value = "Date of booking (yyyy-MM-dd)", dataType = "java.lang.String", required = true, example = "2020-01-01", position = 1)
    private Date date;

    @JsonProperty
    @ApiModelProperty(name = "show", value = "Show selected for booking", required = true, position = 2)
    private Show show;

    @JsonProperty
    @ApiModelProperty(name = "customer", value = "Customer requesting booking", required = true, position = 3)
    private Customer customer;

    @JsonProperty
    @DecimalMax(value = MAX_NO_OF_SEATS_PER_BOOKING, message = "Maximum {value} seats allowed per booking")
    @ApiModelProperty(name = "no of seats", value = "Seats requested to be booked", example = "3", required = true, position = 4)
    private int noOfSeats;

    public Date getDate() {
        return date;
    }

    public Show getShow() {
        return show;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public BookingRequest(){
    }

    public BookingRequest(Date date, Show show, Customer customer, int noOfSeats) {
        this.date = date;
        this.show = show;
        this.customer = customer;
        this.noOfSeats = noOfSeats;
    }
}
