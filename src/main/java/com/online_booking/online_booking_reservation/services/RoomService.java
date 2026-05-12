package com.online_booking.online_booking_reservation.services;

import com.online_booking.online_booking_reservation.dtos.RoomRequestDTO;
import com.online_booking.online_booking_reservation.dtos.RoomResponseDTO;
import com.online_booking.online_booking_reservation.entities.Booking;
import com.online_booking.online_booking_reservation.entities.Room;
import com.online_booking.online_booking_reservation.repositories.BookingRepository;
import com.online_booking.online_booking_reservation.repositories.RoomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @PreAuthorize("hasRole('MANAGER')")
    public RoomResponseDTO createRoom(RoomRequestDTO dto) {
        if (roomRepository.findByRoomNumber(dto.getRoomNumber()).isPresent()) {
            throw new RuntimeException("Room number already exists!");
        }
        Room room = new Room(dto.getRoomNumber(), dto.getRoomType(), dto.getPricePerNight(), dto.getCapacity(),
                Room.RoomStatus.AVAILABLE, dto.getDescription());
        return new RoomResponseDTO(roomRepository.save(room));
    }

    @Autowired
    private CurrencyService currencyService;

    // public List<RoomResponseDTO> getAllRooms() {
    //     Double rate = currencyService.getEtbToUsdRate();
    //     return roomRepository.findAll().stream()
    //             .map(room -> new RoomResponseDTO(room, rate))
    //             .collect(Collectors.toList());
    // }
    @Autowired 
    private BookingRepository bookingRepository;

    public List<RoomResponseDTO> getAllRooms() {
    Double rate = currencyService.getEtbToUsdRate();
    LocalDate today = LocalDate.now();

    return roomRepository.findAll().stream()
            .map(room -> {
                // Check if there is a CONFIRMED booking where today is between check-in and check-out
                boolean isCurrentlyOccupied = bookingRepository.existsByRoomAndStatusAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(
                    room, 
                    Booking.BookingStatus.CONFIRMED, 
                    today, 
                    today
                );

                if (isCurrentlyOccupied) {
                    room.setStatus(Room.RoomStatus.OCCUPIED);
                }
                
                return new RoomResponseDTO(room, rate);
            })
            .collect(Collectors.toList());
}


    
    public RoomResponseDTO getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        return new RoomResponseDTO(room);
    }

    @PreAuthorize("hasRole('MANAGER')")
    public RoomResponseDTO updateRoom(Long id, RoomRequestDTO dto) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        room.setRoomNumber(dto.getRoomNumber());
        room.setRoomType(dto.getRoomType());
        room.setPricePerNight(dto.getPricePerNight());
        room.setCapacity(dto.getCapacity());
        room.setDescription(dto.getDescription());

        if (dto.getStatus() != null) {
            room.setStatus(dto.getStatus());
        }

        return new RoomResponseDTO(roomRepository.save(room));
    }

    @PreAuthorize("hasRole('MANAGER')")
    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new RuntimeException("Room not found");
        }
        roomRepository.deleteById(id);
    }
}