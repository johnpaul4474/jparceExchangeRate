package com.jparce.exchangeRate.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jparce.exchangeRate.model.ExchangeRate;
import com.jparce.exchangeRate.repository.ExchangeRateRepository;

@Service
public class ExchangeRateService {
    private final ExchangeRateRepository exchangeRateRepository;
    
    @Autowired
    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }
    
    @Scheduled(cron = "0 0 0 * * *")
    public ResponseEntity<String> fetchAndSaveExchangeRates() {
        // Fetch exchange rates from API based on AUD 
        RestTemplate restTemplate = new RestTemplate();;
        String url = "https://api.exchangerate-api.com/v4/latest/AUD";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String jsonResponse = response.getBody();
        
		
        
        // Parse JSON and save to database
        ObjectMapper objectMapper = new ObjectMapper();
        try {
        	
            Map<String, Object> map = objectMapper.readValue(jsonResponse, new TypeReference<Map<String, Object>>() {});
            Map<String, Double> rates = (Map<String, Double>) map.get("rates");
            ExchangeRate exchangeRate = new ExchangeRate();
            System.out.println(rates);
            exchangeRate.setUsd(rates.get("USD"));
            exchangeRate.setPhp(rates.get("PHP"));
            exchangeRate.setJpy(rates.get("JPY"));
            exchangeRateRepository.save(exchangeRate);
        } catch (JsonProcessingException e) {
            // Handle parsing exception
            e.printStackTrace();
        }
        
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Success");
    }
}