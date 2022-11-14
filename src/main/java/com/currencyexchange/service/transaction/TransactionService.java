package com.currencyexchange.service.transaction;

import com.currencyexchange.dto.TransactionDTO;

public interface TransactionService {

    TransactionDTO performTransaction(TransactionDTO transactionDTO);
}
