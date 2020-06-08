package com.booking.bookings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query(value = "select count(no_of_seats) from booking where show_id=?1", nativeQuery = true)
    Integer bookedSeatsByShow(Long showId);
}
