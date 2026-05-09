package com.online_booking.online_booking_reservation.services;

import com.online_booking.online_booking_reservation.dtos.BookingRequestDTO;
import com.online_booking.online_booking_reservation.dtos.BookingResponseDTO;
import com.online_booking.online_booking_reservation.entities.*;
import com.online_booking.online_booking_reservation.repositories.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    // CREATE
    public BookingResponseDTO createBooking(BookingRequestDTO dto) {
        User guest = userRepository.findById(dto.getGuestId())
                .orElseThrow(() -> new RuntimeException("Guest not found"));
        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        long nights = ChronoUnit.DAYS.between(dto.getCheckInDate(), dto.getCheckOutDate());
        if (nights <= 0) throw new RuntimeException("Invalid dates: Check-out must be after check-in");

        Booking booking = new Booking();
        booking.setGuest(guest);
        booking.setRoom(room);
        booking.setCheckInDate(dto.getCheckInDate());
        booking.setCheckOutDate(dto.getCheckOutDate());
        booking.setTransactionNum(dto.getTransactionNum());
        booking.setScreenshotUrl(dto.getScreenshotUrl());
        booking.setSenderFullName(dto.getSenderFullName()); 
        booking.setTotalPrice(nights * room.getPricePerNight());
        booking.setStatus(Booking.BookingStatus.PENDING);
        booking.setCreatedAt(LocalDateTime.now());

        Booking saved = bookingRepository.save(booking);
        return new BookingResponseDTO(saved);
    }

    // GET ALL
    public List<BookingResponseDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(BookingResponseDTO::new)
                .collect(Collectors.toList());
    }

    // GET BY USER ID
    public List<BookingResponseDTO> getBookingsByUserId(Long userId) {
        return bookingRepository.findByGuestId(userId).stream()
                .map(BookingResponseDTO::new)
                .collect(Collectors.toList());
    }

    // UPDATE STATUS (Receptionist Feature)
    public BookingResponseDTO updateStatus(Long bookingId, String status, Long receptionistId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        
        User receptionist = userRepository.findById(receptionistId)
                .orElseThrow(() -> new RuntimeException("Receptionist not found"));

        booking.setStatus(Booking.BookingStatus.valueOf(status.toUpperCase()));
        booking.setReceptionist(receptionist);

        return new BookingResponseDTO(bookingRepository.save(booking));
    }

    // Inside BookingService.java
public void deleteBooking(Long id) {
    if (!bookingRepository.existsById(id)) {
        throw new RuntimeException("Booking not found");
    }
    bookingRepository.deleteById(id);
}
}