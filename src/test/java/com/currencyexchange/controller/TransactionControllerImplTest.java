package com.currencyexchange.controller;

import com.currencyexchange.dto.TransactionDTO;
import com.currencyexchange.service.transaction.impl.TransactionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static buider.TransactionDTOBuilder.createTransaction;
import static buider.TransactionDTOBuilder.transactionWithError;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerImplTest {

    private static final String URI = "/v1/transaction";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionServiceImpl transactionServiceImpl;

    @Test
    @DisplayName("POST -> 200 OK")
    void transaction_sucesso() throws Exception {
        TransactionDTO transactionDTO = createTransaction();

        when(transactionServiceImpl.performTransaction(transactionDTO)).thenReturn(transactionDTO);

        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(transactionDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.senderAccountId").exists())
                .andExpect(jsonPath("$.recipientAccountId").exists())
                .andExpect(jsonPath("$.currency").exists())
                .andExpect(jsonPath("$.amount").exists());
    }

    @Test
    @DisplayName("POST -> 400 BadRequest")
    void create_error() throws Exception {
        TransactionDTO transactionDTO = transactionWithError();

        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(transactionDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST -> 500 Internal Server Error")
    void create_error2() throws Exception {
        TransactionDTO transactionDTO = createTransaction();

        when(transactionServiceImpl.performTransaction(transactionDTO)).thenThrow(new RuntimeException("Erro inesperado"));
        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(transactionDTO)))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }
}