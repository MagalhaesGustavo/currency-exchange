package com.currencyexchange.service;

import com.currencyexchange.dto.ExchangeApiDTO;
import com.currencyexchange.exceptions.ExternalMethodFailedException;
import com.currencyexchange.exceptions.NotFoundException;
import com.currencyexchange.exceptions.UnprocessableEntityException;
import com.currencyexchange.integration.exchangerate.ExchangeApiRepository;
import com.currencyexchange.repository.AccountRepository;
import com.currencyexchange.service.transaction.impl.TransactionServiceImpl;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static buider.AccountDomainBuilder.*;
import static buider.ExchangeApiDTOBuilder.createExchangeApiDTO;
import static buider.TransactionDTOBuilder.createSameTransaction;
import static buider.TransactionDTOBuilder.createTransaction;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {

    @InjectMocks
    private TransactionServiceImpl transactionServiceImpl;

    @Mock
    private ExchangeApiRepository exchangeApiRepository;

    @Mock
    private AccountRepository accountRepository;

    @Test
    void createTransaction_successWithoutConversion() {
        val transactionDTO = createTransaction();
        val accountDomainBuilder = createSenderAccount();
        val secondAccountDomain = createRecipientAccount();
        when(accountRepository.findByAccountId(String.valueOf(transactionDTO.getRecipientAccountId()))).thenReturn(Optional.of(accountDomainBuilder));
        when(accountRepository.findByAccountId(String.valueOf(transactionDTO.getSenderAccountId()))).thenReturn(Optional.of(secondAccountDomain));

        val transaction = transactionServiceImpl.performTransaction(transactionDTO);

        verify(accountRepository, times(2)).findByAccountId(any());
        verify(accountRepository, times(1)).saveAll(any());
        assertNotNull(transaction);
        assertEquals(transactionDTO, transaction);
    }

    @Test
    void createTransaction_successWithConversion() throws InterruptedException {
        val transactionDTO = createTransaction();
        val senderAccount = createSenderAccount();
        val recipientAccount = createRecipientAccount();
        val exchangeApiDTO = createExchangeApiDTO();
        senderAccount.setCurrency("EUR");
        recipientAccount.setCurrency("BTC");
        when(accountRepository.findByAccountId(String.valueOf(transactionDTO.getRecipientAccountId()))).thenReturn(Optional.of(recipientAccount));
        when(accountRepository.findByAccountId(String.valueOf(transactionDTO.getSenderAccountId()))).thenReturn(Optional.of(senderAccount));

        when(exchangeApiRepository.convert(transactionDTO.getCurrency(), senderAccount.getCurrency(), String.valueOf(transactionDTO.getAmount()), "2")).thenReturn(exchangeApiDTO);
        when(exchangeApiRepository.convert(transactionDTO.getCurrency(), recipientAccount.getCurrency(), String.valueOf(transactionDTO.getAmount()), "2")).thenReturn(ExchangeApiDTO.builder().result(BigDecimal.valueOf(30)).build());

        val transaction = transactionServiceImpl.performTransaction(transactionDTO);

        verify(accountRepository, times(2)).findByAccountId(any());
        verify(accountRepository, times(1)).saveAll(any());
        assertNotNull(transaction);
        assertEquals(transactionDTO, transaction);
    }

    @Test
    void createTransaction_withConversionFail() throws InterruptedException {
        val transactionDTO = createTransaction();
        var senderAccount = createSenderAccount();
        var recipientAccount = createRecipientAccount();
        senderAccount.setCurrency("EUR");
        recipientAccount.setCurrency("BTC");
        when(accountRepository.findByAccountId(String.valueOf(transactionDTO.getRecipientAccountId()))).thenReturn(Optional.of(recipientAccount));
        when(accountRepository.findByAccountId(String.valueOf(transactionDTO.getSenderAccountId()))).thenReturn(Optional.of(senderAccount));

        when(exchangeApiRepository.convert(transactionDTO.getCurrency(), senderAccount.getCurrency(), String.valueOf(transactionDTO.getAmount()), "2")).thenReturn(null);


        assertThatThrownBy(() -> transactionServiceImpl.performTransaction(transactionDTO)).isInstanceOf(ExternalMethodFailedException.class);

    }

    @Test
    void createTransaction_error_accountNotFound() {
        val transactionDTO = createTransaction();
        when(accountRepository.findByAccountId(anyString())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> transactionServiceImpl.performTransaction(transactionDTO)).isInstanceOf(NotFoundException.class);
        verify(accountRepository, times(1)).findByAccountId(any());
    }

    @Test
    void createTransaction_error_twoEqualAccounts() {
        val transactionDTO = createSameTransaction();
        val accountDomainBuilder = createSenderAccount();
        when(accountRepository.findByAccountId(String.valueOf(transactionDTO.getRecipientAccountId()))).thenReturn(Optional.of(accountDomainBuilder));
        when(accountRepository.findByAccountId(String.valueOf(transactionDTO.getSenderAccountId()))).thenReturn(Optional.of(accountDomainBuilder));
        assertThatThrownBy(() -> transactionServiceImpl.performTransaction(transactionDTO)).isInstanceOf(UnprocessableEntityException.class);
        verify(accountRepository, times(2)).findByAccountId(any());
    }

    @Test
    void createTransaction_error_accountWithoutEnoughMoney() {
        val transactionDTO = createTransaction();
        val accountDomainBuilder = createAccountDomainWithoutMoney();
        val secondAccountDomain = createRecipientAccount();

        when(accountRepository.findByAccountId(String.valueOf(transactionDTO.getSenderAccountId()))).thenReturn(Optional.of(accountDomainBuilder));
        when(accountRepository.findByAccountId(String.valueOf(transactionDTO.getRecipientAccountId()))).thenReturn(Optional.of(secondAccountDomain));

        assertThatThrownBy(() -> transactionServiceImpl.performTransaction(transactionDTO)).isInstanceOf(UnprocessableEntityException.class);
        verify(accountRepository, times(2)).findByAccountId(any());
    }
}
