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
      private Double priceUsd; 

    public RoomResponseDTO(Room room) {
        this.id = room.getId();
        this.roomNumber = room.getRoomNumber();
        this.roomType = room.getRoomType().name();
        this.pricePerNight = room.getPricePerNight();
        this.capacity = room.getCapacity();
        this.status = room.getStatus().name();
        this.description = room.getDescription();
        this.priceUsd = 0.0; 
    }

      //  NEW CONSTRUCTOR for the Map logic (With conversion)
    public RoomResponseDTO(Room room, Double usdRate) {
        this(room); // Calls the constructor above to fill basic fields
        // Calculate USD and round to 2 decimal places
        this.priceUsd = Math.round((room.getPricePerNight() * usdRate) * 100.0) / 100.0;
    }

    // Getters
    public Long getId() { return id; }
    public String getRoomNumber() { return roomNumber; }
    public String getRoomType() { return roomType; }
    public Double getPricePerNight() { return pricePerNight; }
    public Integer getCapacity() { return capacity; }
    public String getStatus() { return status; }
    public String getDescription() { return description; }
    public Double getPriceUsd() { return priceUsd; }
}