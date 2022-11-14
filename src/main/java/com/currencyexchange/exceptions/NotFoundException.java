package com.currencyexchange.exceptions;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    public NotFoundException(String error) {
        super(error);
    }
}