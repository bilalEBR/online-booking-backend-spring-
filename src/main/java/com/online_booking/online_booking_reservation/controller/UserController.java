package com.online_booking.online_booking_reservation.controller;

import com.online_booking.online_booking_reservation.dtos.UserRequestDTO;
import com.online_booking.online_booking_reservation.entities.User;
import com.online_booking.online_booking_reservation.services.UserService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // POST: http://localhost:8080/api/users/register
  @PostMapping("/register")
public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequestDTO dto) {
    try {
        User createdUser = userService.createUser(dto);
        return ResponseEntity.ok(createdUser);
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
}