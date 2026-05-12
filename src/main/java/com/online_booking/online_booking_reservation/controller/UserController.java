package com.online_booking.online_booking_reservation.controller;

import com.online_booking.online_booking_reservation.dtos.UserRequestDTO;
import com.online_booking.online_booking_reservation.dtos.UserResponseDTO;
import com.online_booking.online_booking_reservation.entities.User;
import com.online_booking.online_booking_reservation.services.UserService;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
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

    
@PostMapping("/register")
public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequestDTO dto) {
    try {
        
        UserResponseDTO createdUser = userService.createUser(dto); 
        return ResponseEntity.ok(createdUser);
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}


@PostMapping("/staff")
@PreAuthorize("hasRole('MANAGER')")
public ResponseEntity<?> createStaff(@Valid @RequestBody UserRequestDTO dto) {
    try {
        
        UserResponseDTO createdStaff = userService.createStaff(dto);
        return ResponseEntity.ok(createdStaff);
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

// Get all users (For Manager)
@GetMapping
  @PreAuthorize("hasRole('MANAGER')")
public List<UserResponseDTO> getAll() {
    return userService.getAllUsers();
}

// Get one user
@GetMapping("/{id}")
 @PreAuthorize("hasAnyRole('MANAGER', 'RECEPTIONIST')")
public ResponseEntity<UserResponseDTO> getOne(@PathVariable Long id) {
    return ResponseEntity.ok(userService.getUserById(id));
}

// Update user
@PutMapping("/{id}")
@PreAuthorize("hasRole('MANAGER')")
public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody UserRequestDTO dto) {
    try {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

// Delete user
@DeleteMapping("/{id}")
  @PreAuthorize("hasRole('MANAGER')")
public ResponseEntity<?> delete(@PathVariable Long id) {
    try {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
}