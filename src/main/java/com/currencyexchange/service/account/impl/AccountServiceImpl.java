package com.currencyexchange.service.account.impl;


import com.currencyexchange.domain.AccountDomain;
import com.currencyexchange.dto.AccountDTORequest;
import com.currencyexchange.dto.AccountDTOResponse;
import com.currencyexchange.exceptions.NotFoundException;
import com.currencyexchange.mapper.AccountMapper;
import com.currencyexchange.repository.AccountRepository;
import com.currencyexchange.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.currencyexchange.Utils.MonetaryUtils.validateCurrency;
import static java.util.stream.Collectors.toList;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public AccountDTOResponse createAccount(AccountDTORequest accountDTORequest) {
        validateCurrency(accountDTORequest.getCurrency());
        accountDTORequest.getOwner().setId(autoGenerateId());
        return AccountMapper.toDTO(accountRepository.insert(AccountDomain.valueOf(accountDTORequest)));
    }

    public AccountDTOResponse getAccountByAccountId(String accountId) {
        return accountRepository.findByAccountId(accountId)
                .map(AccountMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Account not found"));
    }

    public List<AccountDTOResponse> getAllAccounts() {
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

    private int autoGenerateId() {
        return accountRepository.findFirstByOrderByOwnerDesc().map(ownerDomain -> ownerDomain.getOwner().getId() + 1).orElse(1);
    }
}