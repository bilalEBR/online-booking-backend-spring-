package com.online_booking.online_booking_reservation.controller;

import com.online_booking.online_booking_reservation.dtos.RoomRequestDTO;
import com.online_booking.online_booking_reservation.dtos.RoomResponseDTO;
import com.online_booking.online_booking_reservation.services.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")

public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    // CREATE
    @PostMapping
   @PreAuthorize("hasRole('MANAGER')")

    public ResponseEntity<?> addRoom(@Valid @RequestBody RoomRequestDTO dto) {
        try {
            return ResponseEntity.ok(roomService.createRoom(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // READ ALL
    @GetMapping
  
    public List<RoomResponseDTO> getAllRooms() {
        return roomService.getAllRooms();
    }

    // READ ONE
    @GetMapping("/{id}")
   

    public ResponseEntity<?> getRoomById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(roomService.getRoomById(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // UPDATE
    @PutMapping("/{id}")
        @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> updateRoom(@PathVariable Long id, @Valid @RequestBody RoomRequestDTO dto) {
        try {
            return ResponseEntity.ok(roomService.updateRoom(id, dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> deleteRoom(@PathVariable Long id) {
        try {
            roomService.deleteRoom(id);
            return ResponseEntity.ok("Room deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}