package com.online_booking.online_booking_reservation.repositories;

import com.online_booking.online_booking_reservation.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomNumber(String roomNumber);
}