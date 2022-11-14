package com.currencyexchange.mapper;

import com.currencyexchange.domain.AccountDomain;
import com.currencyexchange.domain.OwnerDomain;
import com.currencyexchange.dto.AccountDTO;
import com.currencyexchange.dto.OwnerDTO;

public interface AccountMapper {

    static AccountDTO toDTO(AccountDomain accountDomain) {
        return AccountDTO.builder()
                .owner(buildOwner(accountDomain.getOwner()))
                .balance(accountDomain.getBalance())
                .currency(accountDomain.getCurrency())
                .accountId(accountDomain.getAccountId())
                .build();
    }

    static OwnerDTO buildOwner(OwnerDomain owner) {
        return OwnerDTO.builder()
                .id(owner.getId())
                .name(owner.getName())
                .build();
    }
}
