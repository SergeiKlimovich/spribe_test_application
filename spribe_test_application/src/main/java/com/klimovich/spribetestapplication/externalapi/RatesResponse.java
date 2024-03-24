package com.klimovich.spribetestapplication.externalapi;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class RatesResponse {
    private Map<String, Double> rates;
}
