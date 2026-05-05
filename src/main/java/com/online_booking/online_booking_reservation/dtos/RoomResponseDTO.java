package com.online_booking.online_booking_reservation.dtos;

import com.online_booking.online_booking_reservation.entities.Room;

public class RoomResponseDTO {
    private Long id;
    private String roomNumber;
    private String roomType;
    private Double pricePerNight;
    private Integer capacity;
    private String status;
    private String description;

    public RoomResponseDTO(Room room) {
        this.id = room.getId();
        this.roomNumber = room.getRoomNumber();
        this.roomType = room.getRoomType().name();
        this.pricePerNight = room.getPricePerNight();
        this.capacity = room.getCapacity();
        this.status = room.getStatus().name();
        this.description = room.getDescription();
    }

    // Getters
    public Long getId() { return id; }
    public String getRoomNumber() { return roomNumber; }
    public String getRoomType() { return roomType; }
    public Double getPricePerNight() { return pricePerNight; }
    public Integer getCapacity() { return capacity; }
    public String getStatus() { return status; }
    public String getDescription() { return description; }
}