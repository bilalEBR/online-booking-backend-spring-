package com.online_booking.online_booking_reservation.controller;

import com.online_booking.online_booking_reservation.dtos.ReservationRequest;
import com.online_booking.online_booking_reservation.entities.Reservation;
import com.online_booking.online_booking_reservation.services.ReservationService;
import jakarta.validation.Valid; // <--- Important!
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class ReservationController {

    @Autowired
    private ReservationService service;

    @PostMapping
    // Adding @Valid triggers the validation rules inside the DTO
    public Reservation makeBooking(@Valid @RequestBody ReservationRequest request) {
        return service.createBooking(request.getGuestName(), request.getRoomType());
    }
}