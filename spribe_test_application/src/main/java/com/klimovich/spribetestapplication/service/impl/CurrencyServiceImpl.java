package com.klimovich.spribetestapplication.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.klimovich.spribetestapplication.dto.CurrencyDTO;
import com.klimovich.spribetestapplication.externalapi.ExchangeRateApiClient;
import com.klimovich.spribetestapplication.model.Currency;
import com.klimovich.spribetestapplication.repository.CurrencyRepository;
import com.klimovich.spribetestapplication.service.CurrencyService;
import com.klimovich.spribetestapplication.storage.CurrencyRatesStorage;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyRatesStorage currencyRatesStorage;
    private final ExchangeRateApiClient exchangeRatesApiClient;

    @Override
    public List<CurrencyDTO> getAllCurrencies() {
        return currencyRatesStorage.getAllCurrencies();
    }

    @Override
    public Double getExchangeRateByCode(String code) {
        return currencyRatesStorage.getExchangeRateByCurrencyCode(code);
    }

    @Override
    @Transactional
    public void saveCurrency(String code) {
        if (currencyRepository.findByCode(code).isPresent()) {
            throw new RuntimeException("Currency already exists");
        }
        try {
            Double rate = exchangeRatesApiClient.getExchangeRateByCode(code);
            createCurrencyRates(code, rate);
        } catch (IOException e) {
            throw new RuntimeException("Exchange rate getting is failed: " + e.getMessage());
        }
    }

    private void createCurrencyRates( String code, Double rate) {
        Currency currency = new Currency();
        saveCurrency(currency, code, rate);
    }

    @Transactional
    public void updateCurrencyRates(String code, Double rate) {
        Optional<Currency> currency = currencyRepository.findByCode(code);
        currency.ifPresent(value -> saveCurrency(value, code, rate));
    }

    private void saveCurrency(Currency currency, String code, Double rate) {
        currency.setCode(code);
        currency.setExchangeRate(rate);
        currencyRepository.save(currency);
        currencyRatesStorage.updateCurrencyRates(code, rate);
    }
}

