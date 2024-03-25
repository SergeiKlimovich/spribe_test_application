package com.klimovich.spribetestapplication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.klimovich.spribetestapplication.externalapi.ExchangeRateApiClient;
import com.klimovich.spribetestapplication.model.Currency;
import com.klimovich.spribetestapplication.repository.CurrencyRepository;
import com.klimovich.spribetestapplication.service.impl.CurrencyServiceImpl;
import com.klimovich.spribetestapplication.storage.CurrencyRatesStorage;

public class CurrencyServiceImplTest {
    @Mock
    CurrencyRatesStorage currencyRatesCache;
    @Mock
    private CurrencyRepository currencyRepository;
    @Mock
    private ExchangeRateApiClient exchangeRateApiClient;
    @InjectMocks
    private CurrencyServiceImpl currencyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCurrencies() {
        assertNotNull(currencyService.getAllCurrencies());
    }

    @Test
    void getExchangeRate() {
        when(currencyRatesCache.getExchangeRateByCurrencyCode("USD")).thenReturn(1.0);

        assertEquals(1.0, currencyService.getExchangeRateByCode("USD"));
    }

    @Test
    void addCurrency_Success() throws IOException {
        when(currencyRepository.findByCode("USD")).thenReturn(Optional.empty());
        when(exchangeRateApiClient.getExchangeRateByCode("USD")).thenReturn(1.0);

        currencyService.saveCurrency("USD");

        verify(currencyRepository, times(1)).save(any(Currency.class));
        verify(currencyRatesCache, times(1)).updateCurrencyRates("USD", 1.0);
    }

    @Test
    void addCurrency_CurrencyExists() {
        when(currencyRepository.findByCode("USD")).thenReturn(Optional.of(new Currency()));

        assertThrows(RuntimeException.class, () -> currencyService.saveCurrency("USD"));
    }

    @Test
    void addCurrency_Failure() throws IOException {
        when(exchangeRateApiClient.getExchangeRateByCode("INVALID")).thenThrow(new IOException("Error"));

        assertThrows(RuntimeException.class, () -> currencyService.saveCurrency("INVALID"));
    }
}
