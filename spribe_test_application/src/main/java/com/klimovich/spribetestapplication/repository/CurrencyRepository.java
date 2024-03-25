package com.klimovich.spribetestapplication.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.klimovich.spribetestapplication.model.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Optional<Currency> findByCode(String currencyCode);
}
