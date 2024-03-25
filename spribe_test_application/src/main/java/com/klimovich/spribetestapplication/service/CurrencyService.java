package com.klimovich.spribetestapplication.service;

import java.util.List;

import com.klimovich.spribetestapplication.dto.CurrencyDTO;

public interface CurrencyService {
    List<CurrencyDTO> getAllCurrencies();

    Double getExchangeRateByCode(String code);

    void saveCurrency(String code);
     void updateCurrencyRates(String code, Double rate);

}
