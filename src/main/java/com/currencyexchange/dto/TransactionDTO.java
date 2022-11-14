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
public class TransactionDTO {

    @NotNull
    private Integer senderAccountId;
    @NotNull
    private Integer recipientAccountId;
    @PositiveOrZero
    @NotNull
    private BigDecimal amount;
    @NotBlank
    private String currency;
    private BigDecimal rate;
}
