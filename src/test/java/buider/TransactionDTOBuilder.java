package buider;

import com.currencyexchange.dto.TransactionDTO;
import lombok.val;
import org.bson.types.ObjectId;

import java.math.BigDecimal;

public class TransactionDTOBuilder {

    public static TransactionDTO createTransaction() {
        return TransactionDTO.builder()
                .senderAccountId(new ObjectId().toString())
                .recipientAccountId(new ObjectId().toString())
                .currency("BRL")
                .amount(BigDecimal.valueOf(10))
                .build();
    }

    public static TransactionDTO transactionWithError() {
        return TransactionDTO.builder()
                .build();
    }

    public static TransactionDTO createSameTransaction() {
        val accountId = new ObjectId().toString();
        return TransactionDTO.builder()
                .senderAccountId(accountId)
                .recipientAccountId(accountId)
                .currency("BRL")
                .amount(BigDecimal.valueOf(10))
                .build();
    }
}
