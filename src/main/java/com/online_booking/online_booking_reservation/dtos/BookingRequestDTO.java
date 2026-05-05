package com.online_booking.online_booking_reservation.dtos;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class BookingRequestDTO {

    @NotNull(message = "Guest ID is required")
    private Long guestId;

    @NotNull(message = "Room ID is required")
    private Long roomId;

    @NotNull(message = "Check-in date is required")
    @FutureOrPresent(message = "Check-in date cannot be in the past")
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is required")
    private LocalDate checkOutDate;

    @NotBlank(message = "Transaction number is required")
    private String transactionNum;

    private String screenshotUrl;

    // Getters and Setters
    public Long getGuestId() { return guestId; }
    public void setGuestId(Long guestId) { this.guestId = guestId; }
    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }
    public LocalDate getCheckInDate() { return checkInDate; }
    public void setCheckInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; }
    public LocalDate getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; }
    public String getTransactionNum() { return transactionNum; }
    public void setTransactionNum(String transactionNum) { this.transactionNum = transactionNum; }
    public String getScreenshotUrl() { return screenshotUrl; }
    public void setScreenshotUrl(String screenshotUrl) { this.screenshotUrl = screenshotUrl; }
}
