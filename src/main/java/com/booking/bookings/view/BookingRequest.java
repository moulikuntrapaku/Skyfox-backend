package com.booking.bookings.view;

import com.booking.customers.Customer;
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
    @ApiModelProperty(name = "showId", value = "The show id", required = true, position = 2)
    private Long showId;

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

    public Long getShowId() {
        return showId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public BookingRequest() {
    }

    public BookingRequest(Date date, Long showId, Customer customer, int noOfSeats) {
        this.date = date;
        this.showId = showId;
        this.customer = customer;
        this.noOfSeats = noOfSeats;
    }
}
