package com.currencyexchange.service.account;

import com.currencyexchange.dto.AccountDTO;

import java.util.List;

public interface AccountService {

    AccountDTO createAccount(AccountDTO accountDTO);

    AccountDTO getAccountByAccountId(String accountId);

    List<AccountDTO> getAllAccounts();

    void deleteAccountByAccountId(String accountId);
}
