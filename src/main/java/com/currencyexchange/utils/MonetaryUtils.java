package com.currencyexchange.utils;

import org.springframework.stereotype.Component;

import javax.money.Monetary;

@Component
public class MonetaryUtils {

    public static final String DECIMAL_PLACES = "2";

    public static void validateCurrency(String currency) {Monetary.getCurrency(currency);}

    public static boolean currenciesAreDifferent(String currency, String currencyToCompare) {
        return !currency.equals(currencyToCompare);
    }
}
