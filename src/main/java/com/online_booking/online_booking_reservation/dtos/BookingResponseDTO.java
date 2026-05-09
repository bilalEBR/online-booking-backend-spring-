package com.online_booking.online_booking_reservation.dtos;

import com.online_booking.online_booking_reservation.entities.Booking;
import java.time.LocalDate;

public class BookingResponseDTO {
    private Long id;
    private String guestName;
    private String roomNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Double totalPrice;
    private String status;
    private String transactionNum;
    private String receptionistName;
private String senderFullName;
   
    private String screenshotUrl;

    public BookingResponseDTO(Booking booking) {
        this.id = booking.getId();
        this.guestName = booking.getGuest().getFullName();
        this.roomNumber = booking.getRoom().getRoomNumber();
        this.checkInDate = booking.getCheckInDate();
        this.checkOutDate = booking.getCheckOutDate();
        this.totalPrice = booking.getTotalPrice();
        this.status = booking.getStatus().name();
        this.transactionNum = booking.getTransactionNum();
        if (booking.getReceptionist() != null) {
            this.receptionistName = booking.getReceptionist().getFullName();
        }

        this.screenshotUrl = booking.getScreenshotUrl();
        this.senderFullName = booking.getSenderFullName();
    }

    // Getters
    public Long getId() { return id; }
    public String getGuestName() { return guestName; }
    public String getRoomNumber() { return roomNumber; }
    public LocalDate getCheckInDate() { return checkInDate; }
    public LocalDate getCheckOutDate() { return checkOutDate; }
    public Double getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }
    public String getTransactionNum() { return transactionNum; }
    public String getReceptionistName() { return receptionistName; }
    public String getScreenshotUrl() { return screenshotUrl; }
    public String getSenderFullName() { return senderFullName; }
}