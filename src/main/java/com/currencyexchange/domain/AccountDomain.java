package com.currencyexchange.domain;


import com.currencyexchange.dto.AccountDTO;
import com.currencyexchange.dto.OwnerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@Document(value = "Account")
public class AccountDomain {

    @Id
    @Field("account")
    private int accountId;

    @Field("owner")
    private OwnerDomain owner;

    @Field("currency")
    private String currency;

    @Field("balance")
    private BigDecimal balance;

    public static AccountDomain valueOf(AccountDTO accountDTO) {
        return AccountDomain.builder()
                .owner(buildOwner(accountDTO.getAccountId(), accountDTO.getOwner()))
                .balance(accountDTO.getBalance())
                .currency(accountDTO.getCurrency())
                .accountId(accountDTO.getAccountId())
                .build();
    }

    private static OwnerDomain buildOwner(int id,OwnerDTO owner) {
        return OwnerDomain.builder().id(id).name(owner.getName()).build();
    }
}