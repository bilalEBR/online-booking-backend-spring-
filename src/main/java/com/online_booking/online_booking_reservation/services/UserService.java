package com.online_booking.online_booking_reservation.services;

import com.online_booking.online_booking_reservation.dtos.UserRequestDTO;
import com.online_booking.online_booking_reservation.dtos.UserResponseDTO;
import com.online_booking.online_booking_reservation.entities.User;
import com.online_booking.online_booking_reservation.repositories.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize; 
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.stream.Collectors;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder; 


     public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

   public UserResponseDTO createUser(UserRequestDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use!");
        }

        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        
        // IMPORTANT: Hash the password
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword())); 
        
        user.setPhone(dto.getPhone());
        user.setRole(User.UserRole.GUEST);
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        // Return the safe DTO
        return new UserResponseDTO(
            savedUser.getId(), 
            savedUser.getFullName(), 
            savedUser.getEmail(), 
            savedUser.getPhone(), 
            savedUser.getRole().name()
        );
    }




// Inside UserService.java
@PreAuthorize("hasRole('MANAGER')")
public UserResponseDTO createStaff(UserRequestDTO dto) {
    if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
        throw new RuntimeException("Email already in use!");
    }

    User user = new User();
    user.setFullName(dto.getFullName());
    user.setEmail(dto.getEmail());
    user.setPasswordHash(passwordEncoder.encode(dto.getPassword())); 
    user.setPhone(dto.getPhone());
    user.setRole(User.UserRole.RECEPTIONIST); // Explicitly set role to RECEPTIONIST
    user.setCreatedAt(LocalDateTime.now());

    User saved = userRepository.save(user);
    return new UserResponseDTO(saved.getId(), saved.getFullName(), saved.getEmail(), saved.getPhone(), saved.getRole().name());
}


@PreAuthorize("hasRole('MANAGER')")
public List<UserResponseDTO> getAllUsers() {
    return userRepository.findAll().stream()
            .map(user -> new UserResponseDTO(
                user.getId(), 
                user.getFullName(), 
                user.getEmail(), 
                user.getPhone(), 
                user.getRole().toString()))
            .collect(Collectors.toList());
}

@PreAuthorize("hasAnyRole('MANAGER', 'RECEPTIONIST')")
public UserResponseDTO getUserById(Long id) {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
    return new UserResponseDTO(user.getId(), user.getFullName(), user.getEmail(), user.getPhone(), user.getRole().toString());
}

@PreAuthorize("hasRole('MANAGER')")
public void deleteUser(Long id) {
    if (!userRepository.existsById(id)) {
        throw new RuntimeException("Cannot delete: User not found");
    }
    userRepository.deleteById(id);
}


@PreAuthorize("hasRole('MANAGER')")
public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

    // 1. Only check email if it's different from the current one
    if (!user.getEmail().equalsIgnoreCase(dto.getEmail())) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("This email is already taken by another account");
        }
        user.setEmail(dto.getEmail());
    }

    // 2. Update basic info
    user.setFullName(dto.getFullName());
    user.setPhone(dto.getPhone());

    // 3. SECURE PASSWORD CHECK
    // Only update password if it's NOT the placeholder and NOT empty
    if (dto.getPassword() != null && 
        !dto.getPassword().isEmpty() && 
        !dto.getPassword().equals("EXISTING_USER")) {
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
    }

    User updated = userRepository.save(user);
    
    return new UserResponseDTO(
        updated.getId(), 
        updated.getFullName(), 
        updated.getEmail(), 
        updated.getPhone(), 
        updated.getRole().name()
    );
}
}