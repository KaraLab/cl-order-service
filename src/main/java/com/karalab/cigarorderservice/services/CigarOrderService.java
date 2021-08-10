package com.karalab.cigarorderservice.services;

import com.karalab.cigarorderservice.web.model.CigarOrderDto;
import com.karalab.cigarorderservice.web.model.CigarOrderPagedList;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CigarOrderService {

    CigarOrderPagedList listOrders(UUID customerId, Pageable pageable);

    CigarOrderDto placeOrder(UUID customerId, CigarOrderDto cigarOrderDto);

    CigarOrderDto getOrderById(UUID customerId, UUID orderId);

    void pickupOrder(UUID customerId, UUID orderId);
}
