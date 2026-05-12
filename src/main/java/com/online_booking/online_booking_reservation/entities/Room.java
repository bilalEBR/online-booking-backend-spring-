package com.online_booking.online_booking_reservation.entities;
import jakarta.persistence.*;


@Entity
@Table(name = "rooms")
public class Room {

    public enum RoomType { SINGLE, DOUBLE, SUITE, DELUXE }
    public enum RoomStatus { AVAILABLE, DIRTY, MAINTENANCE,TAKEN }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String roomNumber;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    private Double pricePerNight;
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    @Column(columnDefinition = "TEXT")
    private String description;

    public Room() {}

    public Room(String roomNumber, RoomType roomType, Double pricePerNight, Integer capacity, RoomStatus status, String description) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.capacity = capacity;
        this.status = status;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public RoomType getRoomType() { return roomType; }
    public void setRoomType(RoomType roomType) { this.roomType = roomType; }
    public Double getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(Double pricePerNight) { this.pricePerNight = pricePerNight; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public RoomStatus getStatus() { return status; }
    public void setStatus(RoomStatus status) { this.status = status; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
