package com.online_booking.online_booking_reservation.controller;

import com.online_booking.online_booking_reservation.dtos.LoginRequestDTO;
import com.online_booking.online_booking_reservation.dtos.LoginResponseDTO;
import com.online_booking.online_booking_reservation.entities.User; 
import com.online_booking.online_booking_reservation.repositories.UserRepository;
import com.online_booking.online_booking_reservation.security.JwtService;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.crypto.password.PasswordEncoder; 
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
   
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO dto) {
        
        // 3. Find user by email
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 4. Verify password
        if (!passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }

        // 5. Generate token
        String token = jwtService.generateToken(user.getEmail(),user.getRole().name()); // Pass role to token generation
        
        
        return ResponseEntity.ok(new LoginResponseDTO(
             user.getId(),  
            token, 
            user.getFullName(), 
            user.getEmail(), 
            user.getRole().name()
        ));
    }
}