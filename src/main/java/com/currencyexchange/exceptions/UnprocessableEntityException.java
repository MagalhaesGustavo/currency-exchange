package com.currencyexchange.exceptions;

import lombok.Getter;

@Getter
public class UnprocessableEntityException extends RuntimeException {

    public UnprocessableEntityException(String error) {
        super(error);
    }
}
