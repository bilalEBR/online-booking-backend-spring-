package com.online_booking.online_booking_reservation.controller;

import com.online_booking.online_booking_reservation.dtos.RoomRequestDTO;
import com.online_booking.online_booking_reservation.entities.Room;
import com.online_booking.online_booking_reservation.services.RoomService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

  @PostMapping
public ResponseEntity<?> addRoom(@Valid @RequestBody RoomRequestDTO dto) {
    try {
        return ResponseEntity.ok(roomService.createRoom(dto));
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }
}