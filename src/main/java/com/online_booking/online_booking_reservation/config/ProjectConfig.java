package com.online_booking.online_booking_reservation.config;

import com.online_booking.online_booking_reservation.entities.*;
import com.online_booking.online_booking_reservation.repositories.*;
import com.online_booking.online_booking_reservation.security.JwtAuthenticationFilter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
@EnableMethodSecurity
public class ProjectConfig implements WebMvcConfigurer {

    // --- CORS Configuration ---
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:3000",
                        "https://online-booking-frontend-nextjs.onrender.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Inside SecurityConfig.java
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(java.util.List.of(
                            "http://localhost:3000",
                            "https://online-booking-frontend-nextjs.onrender.com"));
                    corsConfiguration
                            .setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
                    corsConfiguration.setAllowedHeaders(java.util.List.of("Authorization", "Content-Type")); // ALLOW
                                                                                                             // AUTHORIZATION
                    return corsConfiguration;
                }))
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**", "/api/users/register").permitAll()
                        .requestMatchers("/uploads/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/rooms/**").permitAll()

                        // 2. ROOMS: Only Manager can modify room data
                        .requestMatchers(HttpMethod.POST, "/api/rooms/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/api/rooms/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/rooms/**").hasRole("MANAGER")

                        // 3. USERS: High-level user management
                        .requestMatchers("/api/users/staff").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET, "/api/users").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("MANAGER")

                        // 4. BOOKINGS: Staff-only features
                        .requestMatchers(HttpMethod.GET, "/api/bookings").hasAnyRole("MANAGER", "RECEPTIONIST")
                        .requestMatchers(HttpMethod.PATCH, "/api/bookings/*/status")
                        .hasAnyRole("MANAGER", "RECEPTIONIST")
                        .requestMatchers(HttpMethod.DELETE, "/api/bookings/**").hasRole("MANAGER")

                        // 5. CATCH-ALL: Anything else (like creating a booking or viewing own profile)
                        // requires login
                        .anyRequest().authenticated())

                .addFilterBefore(jwtFilter,
                        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Expose the "uploads" folder to the outside world
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }

    // --- Database Seeder Configuration ---
    // @Bean
    // public CommandLineRunner seedDatabase(UserRepository userRepo,
    //         RoomRepository roomRepo,
    //         BookingRepository bookingRepo,
    //         PasswordEncoder passwordEncoder) {
    //     return args -> {
    //         // 1. Seed Users if table is empty
    //         if (userRepo.count() == 0) {
    //             User manager = new User("admin@hotel.com", passwordEncoder.encode("password"), "Bilal Ebrahim",
    //                     "111-222", User.UserRole.MANAGER);
    //             User receptionist = new User("staff@hotel.com", passwordEncoder.encode("password"), "Geleta Birhanu",
    //                     "333-444", User.UserRole.RECEPTIONIST);
    //             User guest = new User("guest@hotel.com", passwordEncoder.encode("password"), "Yeabsra Asfaw", "555-666",
    //                     User.UserRole.GUEST);
    //             User memar = new User("memar@hotel.com", passwordEncoder.encode("password"), "Memar teshome ",
    //                     "555-666", User.UserRole.GUEST);

    //             userRepo.saveAll(Arrays.asList(manager, receptionist, guest, memar));
    //             System.out.println(">> Users seeded successfully.");
    //         }

    //         // 2. Seed Rooms if table is empty
    //         if (roomRepo.count() == 0) {
    //             Room r1 = new Room("101", Room.RoomType.SINGLE, 55.0, 1, Room.RoomStatus.AVAILABLE, "Cozy single bed");
    //             Room r2 = new Room("201", Room.RoomType.DELUXE, 150.0, 2, Room.RoomStatus.AVAILABLE, "Luxury sea view");
    //             Room r3 = new Room("305", Room.RoomType.SUITE, 300.0, 4, Room.RoomStatus.MAINTENANCE,
    //                     "Large family suite");

    //             roomRepo.saveAll(Arrays.asList(r1, r2, r3));
    //             System.out.println(">> Rooms seeded successfully.");
    //         }

    //         // 3. Seed a Sample Booking (to verify relationships)
    //         // 3. Seed a Sample Booking (Updated with Receptionist)
    //         if (bookingRepo.count() == 0) {
    //             User sampleGuest = userRepo.findByEmail("guest@test.com").orElse(null);
    //             User sampleReceptionist = userRepo.findByEmail("staff@hotel.com").orElse(null); // Fetch the
    //                                                                                             // receptionist
    //             Room sampleRoom = roomRepo.findAll().stream().findFirst().orElse(null);

    //             if (sampleGuest != null && sampleRoom != null) {
    //                 Booking booking = new Booking();
    //                 booking.setGuest(sampleGuest);
    //                 booking.setRoom(sampleRoom);

    //                 // Use the new naming here:
    //                 booking.setReceptionist(sampleReceptionist);

    //                 booking.setCheckInDate(LocalDate.now().plusDays(1));
    //                 booking.setCheckOutDate(LocalDate.now().plusDays(3));
    //                 booking.setTotalPrice(sampleRoom.getPricePerNight() * 2);

    //                 // Since a receptionist is already assigned in this seed,
    //                 // we can set the status to CONFIRMED instead of PENDING
    //                 booking.setStatus(Booking.BookingStatus.CONFIRMED);

    //                 booking.setTransactionNum("TXN_SEED_001");
    //                 booking.setCreatedAt(LocalDateTime.now());

    //                 bookingRepo.save(booking);
    //                 System.out.println(">> Sample Booking seeded and confirmed by " + sampleReceptionist.getFullName());
    //             }
    //         }
    //     };
    // }

    

}