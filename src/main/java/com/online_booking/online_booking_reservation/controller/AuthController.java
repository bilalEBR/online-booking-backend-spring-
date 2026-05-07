package com.online_booking.online_booking_reservation.controller;

// 1. ADD MISSING IMPORTS
import com.online_booking.online_booking_reservation.dtos.LoginRequestDTO;
import com.online_booking.online_booking_reservation.dtos.LoginResponseDTO;
import com.online_booking.online_booking_reservation.entities.User; // Import the User entity
import com.online_booking.online_booking_reservation.repositories.UserRepository;
import com.online_booking.online_booking_reservation.security.JwtService;

import org.springframework.beans.factory.annotation.Autowired; // For @Autowired
import org.springframework.security.crypto.password.PasswordEncoder; // For PasswordEncoder
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired 
    private UserRepository userRepository;
    
    @Autowired 
    private PasswordEncoder passwordEncoder;
    
    @Autowired 
    private JwtService jwtService;

    @PostMapping("/login")
    // 2. CHANGE LoginRequest to LoginRequestDTO
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO dto) {
        
        // 3. Find user by email
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 4. Verify password
        if (!passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }

        // 5. Generate token
        String token = jwtService.generateToken(user.getEmail());
        
        // 6. CHANGE AuthResponse to LoginResponseDTO
        return ResponseEntity.ok(new LoginResponseDTO(
             user.getId(),  
            token, 
            user.getFullName(), 
            user.getEmail(), 
            user.getRole().name()
        ));
    }
}