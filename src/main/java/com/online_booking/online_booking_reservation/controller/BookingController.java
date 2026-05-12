package com.online_booking.online_booking_reservation.controller;

import com.online_booking.online_booking_reservation.services.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import com.online_booking.online_booking_reservation.dtos.BookingRequestDTO;
import com.online_booking.online_booking_reservation.dtos.BookingResponseDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.nio.file.Path;



@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

  @PostMapping(value = "/create", consumes = { "multipart/form-data" })
  @PreAuthorize("isAuthenticated()") 
public ResponseEntity<?> makeBooking(
        @RequestPart("booking") @Valid BookingRequestDTO dto,
        @RequestPart("file") MultipartFile file) {
    try {
        // 1. Save the file to a folder named "uploads"
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Paths.get("uploads/" + fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());

        // 2. Set the URL in the DTO
        dto.setScreenshotUrl("/uploads/" + fileName);

        return ResponseEntity.ok(bookingService.createBooking(dto));
    } catch (Exception e) {
        return ResponseEntity.badRequest().body("File upload failed: " + e.getMessage());
    }
}

    @GetMapping
     @PreAuthorize("hasAnyRole('MANAGER', 'RECEPTIONIST')")
    public List<BookingResponseDTO> getAll() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'RECEPTIONIST', 'GUEST')")
    public List<BookingResponseDTO> getByUser(@PathVariable Long userId) {
        return bookingService.getBookingsByUserId(userId);
    }

    // Receptionist confirms or cancels
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('MANAGER', 'RECEPTIONIST')")
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

   
@DeleteMapping("/{id}")
@PreAuthorize("hasAnyRole('MANAGER')")
public ResponseEntity<?> deleteBooking(@PathVariable Long id) {
    try {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok("Booking deleted successfully");
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
}