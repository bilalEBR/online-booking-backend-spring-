package com.online_booking.online_booking_reservation.services;

import com.online_booking.online_booking_reservation.dtos.UserRequestDTO;
import com.online_booking.online_booking_reservation.entities.User;
import com.online_booking.online_booking_reservation.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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
}