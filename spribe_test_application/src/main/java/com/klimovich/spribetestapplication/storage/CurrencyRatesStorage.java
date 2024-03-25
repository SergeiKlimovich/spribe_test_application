package com.klimovich.spribetestapplication.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.klimovich.spribetestapplication.dto.CurrencyDTO;


@Component
public class CurrencyRatesStorage {

    private final Map<String, Double> exchangeRatesMap = new HashMap<>();
    private final List<CurrencyDTO> currencies = new ArrayList<>();

    public List<CurrencyDTO> getAllCurrencies() {
        return currencies;
    }

    public Double getExchangeRateByCurrencyCode(String code) {
        return exchangeRatesMap.get(code);
    }

    public void updateCurrencyRates(String code, Double rate) {
        exchangeRatesMap.put(code, rate);
        currencies.removeIf(c -> c.code().equals(code));
        currencies.add(new CurrencyDTO(code, rate));
    }
}
