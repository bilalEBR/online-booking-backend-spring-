package com.online_booking.online_booking_reservation.repositories;

import com.online_booking.online_booking_reservation.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Helpful for checking if a user already exists during registration
    Optional<User> findByEmail(String email);
}