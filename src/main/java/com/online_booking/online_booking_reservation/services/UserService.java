package com.online_booking.online_booking_reservation.services;

import com.online_booking.online_booking_reservation.dtos.UserRequestDTO;
import com.online_booking.online_booking_reservation.dtos.UserResponseDTO;
import com.online_booking.online_booking_reservation.entities.User;
import com.online_booking.online_booking_reservation.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserRequestDTO dto) {
    if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
        throw new RuntimeException("Email already in use!");
    }

    User user = new User();
    user.setFullName(dto.getFullName());
    user.setEmail(dto.getEmail());
    user.setPasswordHash(dto.getPassword()); // In real app, encode this!
    user.setPhone(dto.getPhone());
    user.setRole(User.UserRole.GUEST); // Default role for registration
    user.setCreatedAt(LocalDateTime.now());

    return userRepository.save(user);
}



// Inside UserService.java

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

public UserResponseDTO getUserById(Long id) {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
    return new UserResponseDTO(user.getId(), user.getFullName(), user.getEmail(), user.getPhone(), user.getRole().toString());
}

public void deleteUser(Long id) {
    if (!userRepository.existsById(id)) {
        throw new RuntimeException("Cannot delete: User not found");
    }
    userRepository.deleteById(id);
}

// Update User (Manager updating a guest/staff)
public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
    
    user.setFullName(dto.getFullName());
    user.setPhone(dto.getPhone());
    // Note: Usually we don't allow changing email here to avoid logic issues
    
    User updated = userRepository.save(user);
    return new UserResponseDTO(updated.getId(), updated.getFullName(), updated.getEmail(), updated.getPhone(), updated.getRole().toString());
}
}