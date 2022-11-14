package com.currencyexchange.service;

import com.currencyexchange.domain.AccountDomain;
import com.currencyexchange.exceptions.NotFoundException;
import com.currencyexchange.exceptions.UnprocessableEntityException;
import com.currencyexchange.repository.AccountRepository;
import com.currencyexchange.service.account.impl.AccountServiceImpl;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static buider.AccountDTOBuilder.createAccount;
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

    @Mock
    private AccountDomain accountDomain;

    @Test
    void createAccount_success() {
        val accountDTOBuilder = createAccount();
        val accountDomainBuilder = createSenderAccount();
        when(accountRepository.findByAccountId(String.valueOf(accountDTOBuilder.getAccountId()))).thenReturn(Optional.empty());
        when(accountRepository.insert(accountDomainBuilder)).thenReturn(accountDomainBuilder);

        val account = accountServiceImpl.createAccount(accountDTOBuilder);

        verify(accountRepository, times(1)).findByAccountId(String.valueOf(accountDTOBuilder.getAccountId()));
        verify(accountRepository, times(1)).insert(accountDomainBuilder);
        assertNotNull(account);
    }

    @Test
    void createAccount_error() {
        val accountDTOBuilder = createAccount();
        when(accountRepository.findByAccountId(String.valueOf(accountDTOBuilder.getAccountId()))).thenReturn(Optional.of(createSenderAccount()));
        assertThatThrownBy(() -> accountServiceImpl.createAccount(accountDTOBuilder)).isInstanceOf(UnprocessableEntityException.class);
    }

    @Test
    void get_success() {
        val accountDTOBuilder = createAccount();
        val accountDomainBuilder = createSenderAccount();
        when(accountRepository.findByAccountId(String.valueOf(accountDTOBuilder.getAccountId()))).thenReturn(Optional.of(accountDomainBuilder));

        val account = accountServiceImpl.getAccountByAccountId(String.valueOf(accountDTOBuilder.getAccountId()));

        verify(accountRepository, times(1)).findByAccountId(String.valueOf(accountDTOBuilder.getAccountId()));
        assertNotNull(account);
        assertEquals(accountDTOBuilder, account);
    }

    @Test
    void get_error() {
        val accountDTOBuilder = createAccount();
        when(accountRepository.findByAccountId(String.valueOf(accountDTOBuilder.getAccountId()))).thenReturn(Optional.empty());
        assertThatThrownBy(() -> accountServiceImpl.getAccountByAccountId(String.valueOf(accountDTOBuilder.getAccountId()))).isInstanceOf(NotFoundException.class);
    }

    @Test
    void getAllAccounts_success() {
        val accountDTOBuilder = createAccount();
        val accountDomainBuilder = createSenderAccount();
        when(accountRepository.findAll()).thenReturn(Arrays.asList(accountDomainBuilder));

        val account = accountServiceImpl.getAllAccounts();

        verify(accountRepository, times(1)).findAll();
        assertNotNull(account);
        assertEquals(Arrays.asList(accountDTOBuilder), account);
    }

    @Test
    void delete_success() {
        val accountDTOBuilder = createAccount();
        val accountDomainBuilder = createSenderAccount();
        when(accountRepository.findByAccountId(String.valueOf(accountDTOBuilder.getAccountId()))).thenReturn(Optional.of(accountDomainBuilder));
        doNothing().when(accountRepository).delete(accountDomainBuilder);

        accountServiceImpl.deleteAccountByAccountId(String.valueOf(accountDTOBuilder.getAccountId()));

        verify(accountRepository, times(1)).delete(accountDomainBuilder);
    }

    @Test
    void delete_error() {
        val accountDTOBuilder = createAccount();
        when(accountRepository.findByAccountId(String.valueOf(accountDTOBuilder.getAccountId()))).thenReturn(Optional.empty());
        assertThatThrownBy(() -> accountServiceImpl.deleteAccountByAccountId(String.valueOf(accountDTOBuilder.getAccountId()))).isInstanceOf(NotFoundException.class);
    }
}