package com.jparce.exchangeRate.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jparce.exchangeRate.model.ExchangeRate;


public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Double> {

}
