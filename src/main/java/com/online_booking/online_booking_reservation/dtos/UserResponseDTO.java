package com.online_booking.online_booking_reservation.dtos;

public class UserResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String role;

    // Constructors
    public UserResponseDTO(Long id, String fullName, String email, String phone, String role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    // Getters only (Response DTOs are usually read-only)
    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getRole() { return role; }
}