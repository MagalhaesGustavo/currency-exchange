package com.currencyexchange.exceptions;

import lombok.Getter;

@Getter
public class ExternalMethodFailedException extends RuntimeException {

    public ExternalMethodFailedException(String error) {
        super(error);
    }
}
