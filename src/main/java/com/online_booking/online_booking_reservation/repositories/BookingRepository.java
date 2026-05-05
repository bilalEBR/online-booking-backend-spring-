package com.online_booking.online_booking_reservation.repositories;

import com.online_booking.online_booking_reservation.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Find all bookings made by a specific guest
    List<Booking> findByGuestId(Long guestId);
}