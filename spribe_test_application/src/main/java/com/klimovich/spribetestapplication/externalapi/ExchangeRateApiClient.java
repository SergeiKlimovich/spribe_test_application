package com.klimovich.spribetestapplication.externalapi;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExchangeRateApiClient {

    private final RestTemplate restTemplate;
    @Value("${exchangerate.api.url}")
    private String url;
    @Value("${exchangerate.api.base}")
    private String baseCurrency;

    public Map<String, Double> getExchangeRates() throws IOException {
        String fullUrl = String.format("%s?base=%s", url, baseCurrency);
        RatesResponse response = restTemplate.getForObject(fullUrl, RatesResponse.class);
        return response.getRates();
    }

    public Double getExchangeRateByCode(String currencyCode) throws IOException {
        Map<String, Double> allExchangeRates = getExchangeRates();
        return allExchangeRates.get(currencyCode);
    }
}
