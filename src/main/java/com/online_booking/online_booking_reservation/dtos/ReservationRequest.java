package com.online_booking.online_booking_reservation.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReservationRequest {

    @NotBlank(message = "Guest name cannot be empty")
    private String guestName;

    @NotBlank(message = "Room type is required")
    private String roomType;

    @NotNull(message = "Number of nights is required")
    @Min(value = 1, message = "You must book at least 1 night")
    private Integer numberOfNights;

    @Email(message = "Please provide a valid email address")
    private String guestEmail;

    // Default Constructor
    public ReservationRequest() {}

    // Getters and Setters
    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getNumberOfNights() {
        return numberOfNights;
    }

    public void setNumberOfNights(Integer numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }
}