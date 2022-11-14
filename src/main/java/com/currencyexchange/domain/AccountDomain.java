package com.currencyexchange.domain;


import com.currencyexchange.dto.AccountDTORequest;
import com.currencyexchange.dto.OwnerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
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
    private ObjectId accountId;

    @Field("owner")
    private OwnerDomain owner;

    @Field("currency")
    private String currency;

    @Field("balance")
    private BigDecimal balance;

    public static AccountDomain valueOf(AccountDTORequest accountDTORequest) {
        return AccountDomain.builder()
                .owner(buildOwner(accountDTORequest.getOwner()))
                .balance(accountDTORequest.getBalance())
                .currency(accountDTORequest.getCurrency())
                .build();
    }

    private static OwnerDomain buildOwner(OwnerDTO owner) {
        return OwnerDomain.builder().id(owner.getId()).name(owner.getName()).build();
    }
}