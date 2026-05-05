package com.online_booking.online_booking_reservation.services;

import com.online_booking.online_booking_reservation.dtos.BookingRequestDTO;
import com.online_booking.online_booking_reservation.entities.*;
import com.online_booking.online_booking_reservation.repositories.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, RoomRepository roomRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    // public Booking createBooking(Long guestId, Long roomId, Booking bookingDetails) {
    //     User guest = userRepository.findById(guestId)
    //             .orElseThrow(() -> new RuntimeException("Guest not found"));
    //     Room room = roomRepository.findById(roomId)
    //             .orElseThrow(() -> new RuntimeException("Room not found"));

    //     // Calculate total price: (Check-out - Check-in) * Price per night
    //     long nights = ChronoUnit.DAYS.between(bookingDetails.getCheckInDate(), bookingDetails.getCheckOutDate());
    //     if (nights <= 0) throw new RuntimeException("Check-out date must be after Check-in date");

    //     bookingDetails.setGuest(guest);
    //     bookingDetails.setRoom(room);
    //     bookingDetails.setTotalPrice(nights * room.getPricePerNight());
    //     bookingDetails.setStatus(Booking.BookingStatus.PENDING);
    //     bookingDetails.setCreatedAt(LocalDateTime.now());

    //     return bookingRepository.save(bookingDetails);
    // }

    public Booking createBooking(BookingRequestDTO dto) {
    User guest = userRepository.findById(dto.getGuestId())
            .orElseThrow(() -> new RuntimeException("Guest not found"));
    Room room = roomRepository.findById(dto.getRoomId())
            .orElseThrow(() -> new RuntimeException("Room not found"));

    long nights = ChronoUnit.DAYS.between(dto.getCheckInDate(), dto.getCheckOutDate());
    if (nights <= 0) throw new RuntimeException("Invalid dates");

    Booking booking = new Booking();
    booking.setGuest(guest);
    booking.setRoom(room);
    booking.setCheckInDate(dto.getCheckInDate());
    booking.setCheckOutDate(dto.getCheckOutDate());
    booking.setTransactionNum(dto.getTransactionNum());
    booking.setScreenshotUrl(dto.getScreenshotUrl());
    booking.setTotalPrice(nights * room.getPricePerNight());
    booking.setStatus(Booking.BookingStatus.PENDING);
    booking.setCreatedAt(LocalDateTime.now());

    return bookingRepository.save(booking);
}
}