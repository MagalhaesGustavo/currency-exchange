package buider;

import com.currencyexchange.dto.ExchangeApiDTO;
import com.currencyexchange.dto.Info;

import java.math.BigDecimal;

public class ExchangeApiDTOBuilder {

    public static ExchangeApiDTO createExchangeApiDTO() {
        return ExchangeApiDTO.builder()
                .info(Info.builder().rate(BigDecimal.valueOf(10)).build())
                .result(BigDecimal.valueOf(50))
                .build();
    }
}