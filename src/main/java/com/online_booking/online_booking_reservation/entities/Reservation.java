package com.online_booking.online_booking_reservation.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "reservations") // Good practice to name the table
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String guestName;
    private String roomType;
    private Double price;

    // 1. Default Constructor (Required by JPA/Hibernate)
    public Reservation() {
    }

    // 2. Parameterized Constructor (Optional, but helpful for creating objects quickly)
    public Reservation(String guestName, String roomType, Double price) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.price = price;
    }

    // 3. Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}