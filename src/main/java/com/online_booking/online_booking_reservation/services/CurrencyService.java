package com.online_booking.online_booking_reservation.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class CurrencyService {

    private final String API_URL = "https://open.er-api.com/v6/latest/ETB";

    public Double getEtbToUsdRate() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            // Call the external Web Service
            Map<String, Object> response = restTemplate.getForObject(API_URL, Map.class);
            
            // Extract the rates map
            Map<String, Object> rates = (Map<String, Object>) response.get("rates");
            
            // Return the USD rate (e.g., 0.017)
            return (Double) rates.get("USD");
        } catch (Exception e) {
            // Fallback rate if the external service is down
            return 0.017; 
        }
    }
}