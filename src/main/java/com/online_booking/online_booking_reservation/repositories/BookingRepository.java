package com.online_booking.online_booking_reservation.repositories;

import com.online_booking.online_booking_reservation.entities.Booking;
import com.online_booking.online_booking_reservation.entities.Room;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Find all bookings made by a specific guest
    List<Booking> findByGuestId(Long guestId);

    boolean existsByRoomAndStatusAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(
    Room room, 
    Booking.BookingStatus status, 
    LocalDate date1, 
    LocalDate date2
);
}