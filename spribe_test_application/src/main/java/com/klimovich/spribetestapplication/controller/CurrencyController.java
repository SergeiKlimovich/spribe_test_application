package com.klimovich.spribetestapplication.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import com.klimovich.spribetestapplication.dto.CurrencyDTO;
import com.klimovich.spribetestapplication.service.CurrencyService;
import com.klimovich.spribetestapplication.service.impl.CurrencyServiceImpl;

@Tag(name = "main_methods")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    public ResponseEntity<List<CurrencyDTO>> getAllCurrencies() {
        return new ResponseEntity<>(currencyService.getAllCurrencies(), HttpStatus.OK);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Double> getExchangeRateByCode(@PathVariable String code) {
        return new ResponseEntity<>(currencyService.getExchangeRateByCode(code), HttpStatus.OK);
    }

    @PostMapping("/{code}")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCurrency(@PathVariable String code) {
        currencyService.saveCurrency(code);
    }
}
