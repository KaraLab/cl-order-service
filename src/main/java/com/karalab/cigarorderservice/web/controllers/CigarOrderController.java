package com.karalab.cigarorderservice.web.controllers;

import com.karalab.cigarorderservice.services.CigarOrderService;
import com.karalab.cigarorderservice.web.model.CigarOrderDto;
import com.karalab.cigarorderservice.web.model.CigarOrderPagedList;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/customers/{customerId}/")
@RestController
public class CigarOrderController {

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 25;

    private final CigarOrderService cigarOrderService;

    public CigarOrderController(CigarOrderService cigarOrderService) {
        this.cigarOrderService = cigarOrderService;
    }

    @GetMapping("orders")
    public CigarOrderPagedList listOrders(@PathVariable("customerId") UUID customerId,
                                          @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                          @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (pageNumber == null || pageNumber < 0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        return cigarOrderService.listOrders(customerId, PageRequest.of(pageNumber, pageSize));
    }

    @PostMapping("orders")
    @ResponseStatus(HttpStatus.CREATED)
    public CigarOrderDto placeOrder(@PathVariable("customerId") UUID customerId,
                                    @RequestBody CigarOrderDto cigarOrderDto) {
        return cigarOrderService.placeOrder(customerId, cigarOrderDto);
    }

    @GetMapping("orders/{orderId}")
    public CigarOrderDto getOrder(@PathVariable("customerId") UUID customerId, @PathVariable("orderId") UUID orderId) {
        return cigarOrderService.getOrderById(customerId, orderId);
    }

    @PutMapping("orders/{orderId}/pickup")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void pickupOrder(@PathVariable("customerId") UUID customerId, @PathVariable("orderId") UUID orderId) {
        cigarOrderService.pickupOrder(customerId, orderId);
    }
}
