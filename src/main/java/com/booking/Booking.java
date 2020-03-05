package com.booking;

import java.util.Objects;

public class Booking {
    private int numberOfSeats;
    private String userId;
    private String showId;

    public Booking() {
    }

    public Booking(int numberOfSeats, String userId, String showId) {
        this.numberOfSeats = numberOfSeats;
        this.userId = userId;
        this.showId = showId;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public String getUserId() {
        return userId;
    }

    public String getShowId() {
        return showId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return numberOfSeats == booking.numberOfSeats &&
                Objects.equals(userId, booking.userId) &&
                Objects.equals(showId, booking.showId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfSeats, userId, showId);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "numberOfSeats=" + numberOfSeats +
                ", userId='" + userId + '\'' +
                ", showId='" + showId + '\'' +
                '}';
    }
}
