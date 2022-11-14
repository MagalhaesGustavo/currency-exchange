package buider;

import com.currencyexchange.domain.AccountDomain;
import com.currencyexchange.domain.OwnerDomain;

import java.math.BigDecimal;

public class AccountDomainBuilder {

    public static AccountDomain createSenderAccount() {
        return AccountDomain.builder()
                .owner(OwnerDomain.builder()
                        .id(1)
                        .name("Gustavo")
                        .build())
                .accountId(1)
                .currency("BRL")
                .balance(BigDecimal.valueOf(100.00))
                .build();
    }

    public static AccountDomain createRecipientAccount() {
        return AccountDomain.builder()
                .owner(OwnerDomain.builder()
                        .id(2)
                        .name("Luis")
                        .build())
                .accountId(2)
                .currency("BRL")
                .balance(BigDecimal.valueOf(200.00))
                .build();
    }

    public static AccountDomain createAccountDomainWithoutMoney() {
        return AccountDomain.builder()
                .owner(OwnerDomain.builder()
                        .id(2)
                        .name("Gustavo")
                        .build())
                .accountId(2)
                .currency("BRL")
                .balance(BigDecimal.valueOf(00.00))
                .build();
    }
}