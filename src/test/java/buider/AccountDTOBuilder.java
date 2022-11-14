package buider;

import com.currencyexchange.dto.AccountDTORequest;
import com.currencyexchange.dto.AccountDTOResponse;
import com.currencyexchange.dto.OwnerDTO;
import org.bson.types.ObjectId;

import java.math.BigDecimal;

public class AccountDTOBuilder {

    public static AccountDTORequest createAccountRequest() {
        return AccountDTORequest.builder()
                .owner(OwnerDTO.builder()
                        .id(1)
                        .name("Gustavo")
                        .build())
                .currency("BRL")
                .balance(BigDecimal.valueOf(100.00))
                .build();
    }

    public static AccountDTOResponse createAccountResponse() {
        return AccountDTOResponse.builder()
                .owner(OwnerDTO.builder()
                        .id(1)
                        .name("Gustavo")
                        .build())
                .currency("BRL")
                .balance(BigDecimal.valueOf(100.00))
                .accountId(new ObjectId().toString())
                .build();
    }

    public static AccountDTORequest accountWithError() {
        return AccountDTORequest.builder()
                .build();
    }
}
