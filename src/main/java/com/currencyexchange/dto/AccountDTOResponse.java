package com.currencyexchange.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTOResponse {

    @NotNull
    private OwnerDTO owner;
    @NotBlank
    private String currency;
    @PositiveOrZero
    @NotNull
    private BigDecimal balance;
    private String accountId;
}
