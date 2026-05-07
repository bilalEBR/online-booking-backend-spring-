package com.online_booking.online_booking_reservation.controller;

import com.online_booking.online_booking_reservation.dtos.UserRequestDTO;
import com.online_booking.online_booking_reservation.dtos.UserResponseDTO;
import com.online_booking.online_booking_reservation.entities.User;
import com.online_booking.online_booking_reservation.services.UserService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
        // Change the service call to return a ResponseDTO
        UserResponseDTO createdUser = userService.createUser(dto); 
        return ResponseEntity.ok(createdUser);
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

// Inside UserController.java

@PostMapping("/staff")
public ResponseEntity<?> createStaff(@Valid @RequestBody UserRequestDTO dto) {
    try {
        // We reuse the service logic but override the role explicitly inside the service
        UserResponseDTO createdStaff = userService.createStaff(dto);
        return ResponseEntity.ok(createdStaff);
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

// Get all users (For Manager)
@GetMapping
public List<UserResponseDTO> getAll() {
    return userService.getAllUsers();
}

// Get one user
@GetMapping("/{id}")
public ResponseEntity<UserResponseDTO> getOne(@PathVariable Long id) {
    return ResponseEntity.ok(userService.getUserById(id));
}

// Update user
@PutMapping("/{id}")
public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody UserRequestDTO dto) {
    try {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

// Delete user
@DeleteMapping("/{id}")
public ResponseEntity<?> delete(@PathVariable Long id) {
    try {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
}