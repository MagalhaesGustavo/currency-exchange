package com.currencyexchange.controller.transaction.impl;

import com.currencyexchange.controller.transaction.TransactionController;
import com.currencyexchange.dto.TransactionDTO;
import com.currencyexchange.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/transaction")
public class TransactionControllerImpl implements TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionDTO> performTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionService.performTransaction(transactionDTO));
    }
}