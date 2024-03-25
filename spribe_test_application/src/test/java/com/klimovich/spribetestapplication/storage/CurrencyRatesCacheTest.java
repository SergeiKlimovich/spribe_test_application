package com.klimovich.spribetestapplication.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import com.klimovich.spribetestapplication.dto.CurrencyDTO;

public class CurrencyRatesCacheTest {
    private CurrencyRatesStorage currencyRatesCache;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        currencyRatesCache = new CurrencyRatesStorage();
    }

    @Test
    void getAllCurrencies_shouldReturnEmptyList() {
        List<CurrencyDTO> result = currencyRatesCache.getAllCurrencies();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getExchangeRate_shouldReturnNull() {
        String nonExistingCurrencyCode = "AAA";
        Double result = currencyRatesCache.getExchangeRateByCurrencyCode(nonExistingCurrencyCode);

        assertNull(result);
    }

    @Test
    void updateCurrencyDetails_shouldAddCurrencyToCache() {
        String currencyCode = "USD";
        Double exchangeRate = 1.0;

        currencyRatesCache.updateCurrencyRates(currencyCode, exchangeRate);

        List<CurrencyDTO> currencies = currencyRatesCache.getAllCurrencies();
        assertEquals(1, currencies.size());

        CurrencyDTO updatedCurrency = currencies.get(0);
        assertEquals(currencyCode, updatedCurrency.code());
        assertEquals(exchangeRate, updatedCurrency.exchangeRate());

        Double updatedExchangeRate = currencyRatesCache.getExchangeRateByCurrencyCode(currencyCode);
        assertEquals(exchangeRate, updatedExchangeRate);
    }

    @Test
    void updateCurrencyDetails_shouldUpdateExistingCurrencyInCache() {
        String currencyCode = "USD";
        Double initialExchangeRate = 1.0;
        Double updatedExchangeRate = 1.5;

        currencyRatesCache.updateCurrencyRates(currencyCode, initialExchangeRate);
        currencyRatesCache.updateCurrencyRates(currencyCode, updatedExchangeRate);

        List<CurrencyDTO> currencies = currencyRatesCache.getAllCurrencies();
        assertEquals(1, currencies.size());

        CurrencyDTO updatedCurrency = currencies.get(0);
        assertEquals(currencyCode, updatedCurrency.code());
        assertEquals(updatedExchangeRate, updatedCurrency.exchangeRate());

        Double finalExchangeRate = currencyRatesCache.getExchangeRateByCurrencyCode(currencyCode);
        assertEquals(updatedExchangeRate, finalExchangeRate);
    }
}
