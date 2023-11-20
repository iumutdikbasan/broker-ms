package com.iumutdikbasan.receiptservice.controller;

import com.iumutdikbasan.receiptservice.dto.ReceiptRequest;
import com.iumutdikbasan.receiptservice.service.ReceiptService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/receipt")
@RequiredArgsConstructor
public class ReceiptController {

    private final ReceiptService receiptService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallBackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> placeReceipt(@RequestBody ReceiptRequest receiptRequest){
        return CompletableFuture.supplyAsync(() ->receiptService.placeReceipt(receiptRequest));

    }

    public CompletableFuture<String> fallBackMethod(ReceiptRequest receiptRequest, RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(()->"Oops! Something went wrong.");
    }
}