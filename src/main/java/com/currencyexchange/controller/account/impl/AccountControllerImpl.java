package com.currencyexchange.controller.account.impl;

import com.currencyexchange.controller.account.AccountController;
import com.currencyexchange.dto.AccountDTO;
import com.currencyexchange.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = "/v1/account")
@RequiredArgsConstructor
public class AccountControllerImpl implements AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody AccountDTO accountDTO) {
        return ResponseEntity.status(CREATED)
                .body(accountService.createAccount(accountDTO));
    }

    @GetMapping(path = "/{accountId}")
    public ResponseEntity<AccountDTO> getAccountByAccountId(@PathVariable("accountId") String accountId) {
        return ResponseEntity.status(OK)
                .body(accountService.getAccountByAccountId(accountId));
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        return ResponseEntity.status(OK)
                .body(accountService.getAllAccounts());
    }

    @DeleteMapping(path = "/{accountId}")
    public ResponseEntity<Void> deleteAccountByAccountId(@PathVariable("accountId") String accountId) {
        this.accountService.deleteAccountByAccountId(accountId);
        return ResponseEntity.noContent().build();
    }
}