package com.online_booking.online_booking_reservation.controller;

import com.online_booking.online_booking_reservation.services.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid; // For Spring Boot 3
import com.online_booking.online_booking_reservation.dtos.BookingRequestDTO;
import com.online_booking.online_booking_reservation.dtos.BookingResponseDTO;
import java.util.List;



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

    @GetMapping
    public List<BookingResponseDTO> getAll() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/user/{userId}")
    public List<BookingResponseDTO> getByUser(@PathVariable Long userId) {
        return bookingService.getBookingsByUserId(userId);
    }

    // Receptionist confirms or cancels
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id, 
            @RequestParam String status, 
            @RequestParam Long receptionistId) {
        try {
            return ResponseEntity.ok(bookingService.updateStatus(id, status, receptionistId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}