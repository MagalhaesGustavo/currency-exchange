package com.currencyexchange.controller;

import com.currencyexchange.dto.AccountDTORequest;
import com.currencyexchange.dto.AccountDTOResponse;
import com.currencyexchange.exceptions.NotFoundException;
import com.currencyexchange.service.account.impl.AccountServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static buider.AccountDTOBuilder.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerImplTest {

    private static final String URI = "/v1/account";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountServiceImpl accountServiceImpl;

    @Test
    @DisplayName("POST -> 200 OK")
    void create_success() throws Exception {
        AccountDTORequest accountDTORequest = createAccountRequest();
        AccountDTOResponse accountDTOResponse = createAccountResponse();

        when(accountServiceImpl.createAccount(accountDTORequest)).thenReturn(accountDTOResponse);

        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(accountDTORequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.owner").exists())
                .andExpect(jsonPath("$.currency").exists())
                .andExpect(jsonPath("$.balance").exists())
                .andExpect(jsonPath("$.accountId").exists());
    }

    @Test
    @DisplayName("POST -> 400 BadRequest")
    void create_error_invalidParameters() throws Exception {
        AccountDTORequest accountDTORequest = accountWithError();

        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(accountDTORequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST -> 500 Internal Server Error")
    void create_error_internalServerError() throws Exception {
        AccountDTORequest accountDTORequest = createAccountRequest();

        when(accountServiceImpl.createAccount(accountDTORequest)).thenThrow(new RuntimeException(""));
        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(accountDTORequest)))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("GET -> 200 OK")
    void get_success() throws Exception {
        AccountDTOResponse accountDTOResponse = createAccountResponse();
        String accountId = accountDTOResponse.getAccountId();

        when(accountServiceImpl.getAccountByAccountId(accountId)).thenReturn(accountDTOResponse);

        mockMvc.perform(get(URI + "/{accountId}", accountId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.owner").exists())
                .andExpect(jsonPath("$.currency").exists())
                .andExpect(jsonPath("$.balance").exists())
                .andExpect(jsonPath("$.accountId").exists());
    }

    @Test
    @DisplayName("GET -> 404 NotFound")
    void get_error_accountNotFound() throws Exception {
        String accountId = "1";

        when(accountServiceImpl.getAccountByAccountId(accountId)).thenThrow(new NotFoundException(""));

        mockMvc.perform(get(URI + "/{accountId}", accountId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET -> 500 Internal Server Error")
    void get_error_internalServerError() throws Exception {
        String accountId = "1";

        when(accountServiceImpl.getAccountByAccountId(accountId)).thenThrow(new RuntimeException(""));

        mockMvc.perform(get(URI + "/{accountId}", accountId))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("GET All -> 200 OK")
    void getAll_success() throws Exception {
        AccountDTOResponse accountDTOResponse = createAccountResponse();

        when(accountServiceImpl.getAllAccounts()).thenReturn(List.of(accountDTOResponse));

        mockMvc.perform(get(URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("GET ALL -> 404 NotFound")
    void getAll_error_accountNotFound() throws Exception {
        when(accountServiceImpl.getAllAccounts()).thenThrow(new NotFoundException(""));

        mockMvc.perform(get(URI))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET ALL -> 500 Internal Server Error")
    void getAll_error_internalServerError() throws Exception {
        when(accountServiceImpl.getAllAccounts()).thenThrow(new RuntimeException(""));

        mockMvc.perform(get(URI))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("DELETE -> 200 OK")
    void delete_success() throws Exception {
        String accountId = "1";

        doNothing().when(accountServiceImpl).deleteAccountByAccountId(accountId);

        mockMvc.perform(delete(URI + "/{accountId}", accountId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE -> 404 NotFound")
    void delete_error_accountNotFound() throws Exception {
        String accountId = "1";

        doThrow(new NotFoundException("")).when(accountServiceImpl).deleteAccountByAccountId(accountId);

        mockMvc.perform(delete(URI + "/{accountId}", accountId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET -> 500 Internal Server Error")
    void delete_error_internalServerError() throws Exception {
        String accountId = "1";

        doThrow(new RuntimeException("")).when(accountServiceImpl).deleteAccountByAccountId(accountId);

        mockMvc.perform(delete(URI + "/{accountId}", accountId))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }
}