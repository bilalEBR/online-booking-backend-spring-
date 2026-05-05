package com.online_booking.online_booking_reservation.controller;

import com.online_booking.online_booking_reservation.services.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid; // For Spring Boot 3
import com.online_booking.online_booking_reservation.dtos.BookingRequestDTO;
// @RestController
// @RequestMapping("/api/bookings")
// public class BookingController {
//     private final BookingService bookingService;

//     public BookingController(BookingService bookingService) {
//         this.bookingService = bookingService;
//     }

//     @PostMapping("/create")
//     public ResponseEntity<?> makeBooking(
//             @RequestParam Long guestId, 
//             @RequestParam Long roomId, 
//             @RequestBody Booking booking) {
//         try {
//             Booking newBooking = bookingService.createBooking(guestId, roomId, booking);
//             return ResponseEntity.ok(newBooking);
//         } catch (Exception e) {
//             return ResponseEntity.badRequest().body(e.getMessage());
//         }
//     }
// }

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> makeBooking(@Valid @RequestBody BookingRequestDTO dto) {
        try {
            // We pass the DTO to the service
            return ResponseEntity.ok(bookingService.createBooking(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}