package com.example.orderservice.controller;


import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.model.Order;
import com.example.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory",fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest){
       return CompletableFuture.supplyAsync(()->orderService.placeOrder(orderRequest));

    }
    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest,RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(()->"Oops Something went wrong");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllOrders(){
        List<Order> allOrders = orderService.getAllOrders();
        return allOrders;
    }
}
