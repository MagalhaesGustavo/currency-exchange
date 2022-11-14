package com.currencyexchange.integration.exchangerate;

import com.currencyexchange.dto.ExchangeApiDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "exchange-api", url = "${exchange-rate-api.baseurl}")
public interface ExchangeApiRepository {

    @GetMapping("/convert")
    ExchangeApiDTO convert(
            @RequestParam(value = "from") final String from,
            @RequestParam(value = "to") final String to,
            @RequestParam(value = "amount") final String amount,
            @RequestParam(value = "places") final String places
    );
}