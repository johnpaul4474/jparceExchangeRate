package com.jparce.exchangeRate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jparce.exchangeRate.model.ExchangeRate;
import com.jparce.exchangeRate.service.ExchangeRateService;

@RestController
@RequestMapping("/rates")
public class ExchangeRateController {
	@Autowired

	private ExchangeRateService exchangeRateService;
	
	@GetMapping("/hello")
	public String hello(){		
		return "hello";
	}
	
	@GetMapping("/exchangeRates")
	public ResponseEntity<String> getExchangeRate(){
		return exchangeRateService.fetchAndSaveExchangeRates();
		
	}
}
