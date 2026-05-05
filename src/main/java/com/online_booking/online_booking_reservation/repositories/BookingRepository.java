package com.online_booking.online_booking_reservation.repositories;

import com.online_booking.online_booking_reservation.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}