package buider;

import com.currencyexchange.dto.AccountDTO;
import com.currencyexchange.dto.OwnerDTO;

import java.math.BigDecimal;

public class AccountDTOBuilder {

    public static AccountDTO createAccount() {
        return AccountDTO.builder()
                .owner(OwnerDTO.builder()
                        .id(1)
                        .name("Gustavo")
                        .build())
                .accountId(1)
                .currency("BRL")
                .balance(BigDecimal.valueOf(100.00))
                .build();
    }

    public static AccountDTO accountWithError() {
        return AccountDTO.builder()
                .build();
    }
}
