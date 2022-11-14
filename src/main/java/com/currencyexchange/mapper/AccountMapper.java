package com.currencyexchange.mapper;

import com.currencyexchange.domain.AccountDomain;
import com.currencyexchange.domain.OwnerDomain;
import com.currencyexchange.dto.AccountDTOResponse;
import com.currencyexchange.dto.OwnerDTO;

import static java.util.Objects.isNull;

public interface AccountMapper {

    static AccountDTOResponse toDTO(AccountDomain accountDomain) {
        return AccountDTOResponse.builder()
                .owner(buildOwner(accountDomain.getOwner()))
                .balance(accountDomain.getBalance())
                .currency(accountDomain.getCurrency())
                .accountId(isNull(accountDomain.getAccountId()) ? null : accountDomain.getAccountId().toString())
                .build();
    }

    static OwnerDTO buildOwner(OwnerDomain owner) {
        return OwnerDTO.builder()
                .id(owner.getId())
                .name(owner.getName())
                .build();
    }
}
