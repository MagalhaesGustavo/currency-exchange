package com.currencyexchange.service.transaction.impl;


import com.currencyexchange.utils.MonetaryUtils;
import com.currencyexchange.domain.AccountDomain;
import com.currencyexchange.dto.ExchangeApiDTO;
import com.currencyexchange.dto.TransactionDTO;
import com.currencyexchange.exceptions.ExternalMethodFailedException;
import com.currencyexchange.exceptions.NotFoundException;
import com.currencyexchange.exceptions.UnprocessableEntityException;
import com.currencyexchange.integration.exchangerate.ExchangeApiRepository;
import com.currencyexchange.repository.AccountRepository;
import com.currencyexchange.service.transaction.TransactionService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;

import static com.currencyexchange.utils.MonetaryUtils.*;
import static java.util.Objects.isNull;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    public static final int VALUE_BIGGER_THEN = 0;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MonetaryUtils monetaryUtils;

    @Autowired
    private ExchangeApiRepository exchangeApiRepository;

    public TransactionDTO performTransaction(TransactionDTO transactionDTO) {
        validateCurrency(transactionDTO.getCurrency());
        val senderAccount = getAccount(transactionDTO.getSenderAccountId());
        val recipientAccount = getAccount(transactionDTO.getRecipientAccountId());
        var usedRate = new BigDecimal(1);

        if (transactionDTO.getRecipientAccountId().equals(transactionDTO.getSenderAccountId()))
            throw new UnprocessableEntityException("It is not possible to transfer money to the same sender accountId " + senderAccount.getAccountId());

        var transactionAmount = transactionDTO.getAmount();

        if (currenciesAreDifferent(senderAccount.getCurrency(), transactionDTO.getCurrency())) {
            val exchangeResponse = convertAmountWithExchangeRate(
                    transactionDTO.getCurrency(),
                    senderAccount.getCurrency(),
                    String.valueOf(transactionDTO.getAmount()));

            if (isNull(exchangeResponse))
                throw new ExternalMethodFailedException("It wasn't possible to recover the exchange rate from an external API");

            usedRate = exchangeResponse.getInfo().getRate();
            transactionAmount = exchangeResponse.getResult();
        }

        if (hasEnoughMoney(senderAccount, transactionAmount)) {
            senderAccount.setBalance(senderAccount.getBalance().subtract(transactionAmount));

            BigDecimal newRecipientBalance;
            if (currenciesAreDifferent(senderAccount.getCurrency(), recipientAccount.getCurrency())) {
                val convertedAmount = convertAmountWithExchangeRate(
                        transactionDTO.getCurrency(),
                        recipientAccount.getCurrency(),
                        String.valueOf(transactionDTO.getAmount()))
                        .getResult();

                newRecipientBalance = recipientAccount.getBalance().add(convertedAmount);
            } else {
                newRecipientBalance = recipientAccount.getBalance().add(transactionAmount);
            }
            recipientAccount.setBalance(newRecipientBalance);

            accountRepository.saveAll(Arrays.asList(senderAccount, recipientAccount));
            transactionDTO.setRate(usedRate);
        } else {
            throw new UnprocessableEntityException("The balance of the debit account is not sufficient. Id: " + senderAccount.getAccountId());
        }

        return transactionDTO;
    }

    private static boolean hasEnoughMoney(AccountDomain account, BigDecimal transactionAmount) {
        return account.getBalance().compareTo(transactionAmount) > VALUE_BIGGER_THEN;
    }

    private AccountDomain getAccount(String accountId) {
        return accountRepository
                .findByAccountId(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found id: " + accountId));
    }

    private ExchangeApiDTO convertAmountWithExchangeRate(String originCurrency, String destinyCurrency, String amount) throws FeignException {
        return exchangeApiRepository.convert(
                originCurrency,
                destinyCurrency,
                amount,
                DECIMAL_PLACES);
    }
}