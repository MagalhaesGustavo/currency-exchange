package com.currencyexchange.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeApiDTO {

    private Motd motd;
    private Boolean success;
    private Query query;
    private Info info;
    private Boolean historical;
    private String date;
    private BigDecimal result;

    public class Motd {
        private String msg;
        private String url;
    }

    public class Query {
        private String from;
        private String to;
        private BigDecimal amount;
    }

}