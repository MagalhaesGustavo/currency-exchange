package com.currencyexchange.service.account;

import com.currencyexchange.dto.AccountDTORequest;
import com.currencyexchange.dto.AccountDTOResponse;

import java.util.List;

public interface AccountService {

    AccountDTOResponse createAccount(AccountDTORequest accountDTORequest);

    AccountDTOResponse getAccountByAccountId(String accountId);

    List<AccountDTOResponse> getAllAccounts();

    void deleteAccountByAccountId(String accountId);
}
