package com.online_booking.online_booking_reservation.dtos;

import com.online_booking.online_booking_reservation.entities.Room;
import jakarta.validation.constraints.*;

public class RoomRequestDTO {

    @NotBlank(message = "Room number is required")
    private String roomNumber;

    @NotNull(message = "Room type is required")
    private Room.RoomType roomType;

    @Positive(message = "Price must be greater than zero")
    private Double pricePerNight;

    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;

    private String description;

     private Room.RoomStatus status;

    // Getters and Setters
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public Room.RoomType getRoomType() { return roomType; }
    public void setRoomType(Room.RoomType roomType) { this.roomType = roomType; }
    public Double getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(Double pricePerNight) { this.pricePerNight = pricePerNight; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
     public Room.RoomStatus getStatus() { return status; }
    public void setStatus(Room.RoomStatus status) { this.status = status; }
}