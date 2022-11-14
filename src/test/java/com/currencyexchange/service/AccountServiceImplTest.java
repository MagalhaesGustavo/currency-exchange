package com.currencyexchange.service;

import com.currencyexchange.exceptions.NotFoundException;
import com.currencyexchange.repository.AccountRepository;
import com.currencyexchange.service.account.impl.AccountServiceImpl;
import lombok.val;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.money.UnknownCurrencyException;
import java.util.List;
import java.util.Optional;

import static buider.AccountDTOBuilder.createAccountRequest;
import static buider.AccountDomainBuilder.createAccountToInsert;
import static buider.AccountDomainBuilder.createSenderAccount;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountServiceImpl;

    @Mock
    private AccountRepository accountRepository;

    @Test
    void createAccount_success() {
        var accountDTOBuilder = createAccountRequest();
        val accountDomainBuilder = createSenderAccount();
        val newAccount = createAccountToInsert();
        when(accountRepository.findFirstByOrderByOwnerDesc()).thenReturn(Optional.of(accountDomainBuilder));
        when(accountRepository.insert(newAccount)).thenReturn(newAccount);
        val account = accountServiceImpl.createAccount(accountDTOBuilder);

        verify(accountRepository, times(1)).findFirstByOrderByOwnerDesc();
        assertNotNull(account);
    }

    @Test
    void createAccount_error_currencyNotValid() {
        var accountDTOBuilder = createAccountRequest();
        accountDTOBuilder.setCurrency("BLA");
        assertThatThrownBy(() -> accountServiceImpl.createAccount(accountDTOBuilder)).isInstanceOf(UnknownCurrencyException.class);
    }

    @Test
    void get_success() {
        var accountDomainBuilder = createSenderAccount();
        when(accountRepository.findByAccountId(accountDomainBuilder.getAccountId().toString())).thenReturn(Optional.of(accountDomainBuilder));

        val account = accountServiceImpl.getAccountByAccountId(String.valueOf(accountDomainBuilder.getAccountId()));

        verify(accountRepository, times(1)).findByAccountId(String.valueOf(accountDomainBuilder.getAccountId()));
        assertNotNull(account);
        assertEquals(accountDomainBuilder.getAccountId().toString(), account.getAccountId());
    }

    @Test
    void get_error() {
        val accountId = new ObjectId().toString();
        when(accountRepository.findByAccountId(accountId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> accountServiceImpl.getAccountByAccountId(accountId)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void getAllAccounts_success() {
        var accountDomainBuilder = createSenderAccount();
        when(accountRepository.findAll()).thenReturn(List.of(accountDomainBuilder));

        val account = accountServiceImpl.getAllAccounts();

        verify(accountRepository, times(1)).findAll();
        assertNotNull(account);
    }

    @Test
    void delete_success() {
        val accountId = new ObjectId().toString();
        val accountDomainBuilder = createSenderAccount();
        when(accountRepository.findByAccountId(accountId)).thenReturn(Optional.of(accountDomainBuilder));
        doNothing().when(accountRepository).delete(accountDomainBuilder);

        accountServiceImpl.deleteAccountByAccountId(accountId);

        verify(accountRepository, times(1)).delete(accountDomainBuilder);
    }

    @Test
    void delete_error() {
        val accountId = new ObjectId().toString();
        when(accountRepository.findByAccountId(accountId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> accountServiceImpl.deleteAccountByAccountId(accountId)).isInstanceOf(NotFoundException.class);
    }
}