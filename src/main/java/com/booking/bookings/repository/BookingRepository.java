package com.booking.bookings.repository;

import com.booking.shows.respository.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collection;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query(value = "SELECT SUM(NO_OF_SEATS) FROM BOOKING WHERE SHOW_ID=?1", nativeQuery = true)
    Integer bookedSeatsByShow(Long showId);

    @Query(value = "SELECT SUM(b.amountPaid) FROM Booking b WHERE b.show IN :shows")
    BigDecimal bookingAmountByShows(@Param("shows") Collection<Show> shows);
}
