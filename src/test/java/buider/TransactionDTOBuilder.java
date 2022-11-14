package buider;

import com.currencyexchange.dto.TransactionDTO;

import java.math.BigDecimal;

public class TransactionDTOBuilder {

    public static TransactionDTO createTransaction() {
        return TransactionDTO.builder()
                .senderAccountId(1)
                .recipientAccountId(2)
                .currency("BRL")
                .amount(BigDecimal.valueOf(10))
                .build();
    }

    public static TransactionDTO transactionWithError() {
        return TransactionDTO.builder()
                .build();
    }
    public static TransactionDTO createSameTransaction() {
        return TransactionDTO.builder()
                .senderAccountId(1)
                .recipientAccountId(1)
                .currency("BRL")
                .amount(BigDecimal.valueOf(10))
                .build();
    }
}
