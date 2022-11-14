package com.currencyexchange.controller.account;

import com.currencyexchange.dto.AccountDTO;
import com.currencyexchange.exceptions.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface AccountController {

    @Tag(name = "Account")
    @Operation(summary = "This API creates a new account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AccountDTO.class))),
            @ApiResponse(responseCode = "404", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class)))})
    @PostMapping
    ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody AccountDTO accountDTO);

    @Tag(name = "Account")
    @Operation(summary = "This API fetches one account by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AccountDTO.class))),
            @ApiResponse(responseCode = "404", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(path = "/{accountId}")
    ResponseEntity<AccountDTO> getAccountByAccountId(@PathVariable("accountId") String accountId);

    @Tag(name = "Account")
    @Operation(summary = "This API fetches all registered accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AccountDTO.class))),
            @ApiResponse(responseCode = "404", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class)))})
    @GetMapping
    ResponseEntity<List<AccountDTO>> getAllAccounts();

    @Tag(name = "Account")
    @Operation(summary = "This API delete an account by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AccountDTO.class))),
            @ApiResponse(responseCode = "404", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class)))})
    @DeleteMapping(path = "/{accountId}")
    ResponseEntity<Void> deleteAccountByAccountId(@PathVariable("accountId") String accountId);
}