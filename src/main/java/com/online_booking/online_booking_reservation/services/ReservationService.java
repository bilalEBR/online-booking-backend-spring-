package com.online_booking.online_booking_reservation.services;

import com.online_booking.online_booking_reservation.entities.Reservation;
import com.online_booking.online_booking_reservation.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository repository;

    public Reservation createBooking(String name, String type) {
        Reservation res = new Reservation();
        res.setGuestName(name);
        res.setRoomType(type);
        res.setPrice(type.equalsIgnoreCase("Suite") ? 200.0 : 100.0); // Simple logic
        return repository.save(res);
    }

    public List<Reservation> getAllBookings() {
        return repository.findAll();
    }
}