package com.klimovich.spribetestapplication.scheduled;

import java.io.IOException;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.klimovich.spribetestapplication.externalapi.ExchangeRateApiClient;
import com.klimovich.spribetestapplication.service.CurrencyService;
import com.klimovich.spribetestapplication.service.impl.CurrencyServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExchangeRateScheduler {

    private final ExchangeRateApiClient exchangeRatesApiClient;

    private final CurrencyServiceImpl currencyService;

    @Scheduled(fixedRateString = "${exchange.rate.updater.fixed-rate}")
    public void updateExchangeRates() {
        try {
            Map<String, Double> exchangeRates = exchangeRatesApiClient.getExchangeRates();
            exchangeRates.forEach(currencyService::updateCurrencyRates);
        } catch (IOException e) {
            throw new RuntimeException("Exchange rate updating is failed:" + e.getMessage());
        }
    }
}
