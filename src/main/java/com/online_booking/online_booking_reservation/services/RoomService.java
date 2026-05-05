package com.online_booking.online_booking_reservation.services;

import com.online_booking.online_booking_reservation.dtos.RoomRequestDTO;
import com.online_booking.online_booking_reservation.entities.Room;
import com.online_booking.online_booking_reservation.repositories.RoomRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

   public Room createRoom(RoomRequestDTO dto) {
    if(roomRepository.findByRoomNumber(dto.getRoomNumber()).isPresent()) {
        throw new RuntimeException("Room number " + dto.getRoomNumber() + " already exists!");
    }
    
    Room room = new Room();
    room.setRoomNumber(dto.getRoomNumber());
    room.setRoomType(dto.getRoomType());
    room.setPricePerNight(dto.getPricePerNight());
    room.setCapacity(dto.getCapacity());
    room.setDescription(dto.getDescription());
    room.setStatus(Room.RoomStatus.AVAILABLE); // Default status
    
    return roomRepository.save(room);
}

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }
}