package com.currencyexchange.service.account.impl;


import com.currencyexchange.domain.AccountDomain;
import com.currencyexchange.dto.AccountDTO;
import com.currencyexchange.exceptions.NotFoundException;
import com.currencyexchange.exceptions.UnprocessableEntityException;
import com.currencyexchange.mapper.AccountMapper;
import com.currencyexchange.repository.AccountRepository;
import com.currencyexchange.service.account.AccountService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.currencyexchange.Utils.MonetaryUtils.validateCurrency;
import static java.util.stream.Collectors.toList;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public AccountDTO createAccount(AccountDTO accountDTO) {
        validateCurrency(accountDTO.getCurrency());
        val existsAccountId = accountRepository.findByAccountId(String.valueOf(accountDTO.getAccountId()));
        if (existsAccountId.isPresent())
            throw new UnprocessableEntityException("Account already exists");
        return AccountMapper.toDTO(accountRepository.insert(AccountDomain.valueOf(accountDTO)));
    }

    public AccountDTO getAccountByAccountId(String accountId) {
        return accountRepository.findByAccountId(accountId)
                .map(AccountMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Account not found"));
    }

    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(AccountMapper::toDTO)
                .collect(toList());
    }

    public void deleteAccountByAccountId(String accountId) {
        accountRepository.delete(
                accountRepository.findByAccountId(accountId)
                        .orElseThrow(() -> new NotFoundException("Account not found")));
    }
}