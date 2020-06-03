package com.booking.bookings.repository;

import com.booking.shows.respository.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    long countByShow(Show show);
}
