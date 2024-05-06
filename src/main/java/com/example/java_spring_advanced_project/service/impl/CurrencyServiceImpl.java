package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.entity.Currency;
import com.example.java_spring_advanced_project.model.entity.enums.CurrencyName;
import com.example.java_spring_advanced_project.repository.CurrencyRepository;
import com.example.java_spring_advanced_project.service.CurrencyService;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public void initCurrencies() {
        if (currencyRepository.count() == 0) {
            Arrays.stream(CurrencyName.values())
                    .forEach(currencyName -> {
                        Currency currency = new Currency();
                        currency.setCurrency(currencyName);
                        currency.setOriginCountry(getCurrencyCountry(currencyName));
                        currency.setToEuro(getToEuroForCurrency(currencyName));
                        currencyRepository.save(currency);
                    });
        }
    }

    private double getToEuroForCurrency(CurrencyName currencyName) {

        return switch (currencyName) {
            case Lev -> 0.51129;
            case Euro -> 1.0;
            case Dollar -> 0.8345;
            case Yen -> 0.0076;
            case Rubles -> 0.0108;
            case Pounds -> 1.155;
            default -> throw new IllegalArgumentException("Invalid currency type");
        };
    }
    private String getCurrencyCountry(CurrencyName currencyName) {

        return switch (currencyName) {
            case Lev -> "Bulgaria";
            case Euro -> "EU";
            case Dollar -> "United States";
            case Yen -> "Japan";
            case Rubles -> "Russia";
            case Pounds -> "United Kingdom";
            default -> throw new IllegalArgumentException("Invalid currency type");
        };
    }
}