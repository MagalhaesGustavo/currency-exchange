package com.currencyexchange.controller.transaction;

import com.currencyexchange.dto.TransactionDTO;
import com.currencyexchange.exceptions.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface TransactionController {
    @Tag(name = "Transaction")
    @Operation(summary = "This API performs transactions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TransactionDTO.class))),
            @ApiResponse(responseCode = "404", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class)))})
    @PostMapping
    ResponseEntity<TransactionDTO> performTransaction(@Valid @RequestBody TransactionDTO transactionDTO);
}
