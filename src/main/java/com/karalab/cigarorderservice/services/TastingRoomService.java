package com.karalab.cigarorderservice.services;

import com.karalab.cigarorderservice.bootstrap.CigarOrderBootStrap;
import com.karalab.cigarorderservice.domain.Customer;
import com.karalab.cigarorderservice.repositories.CigarOrderRepository;
import com.karalab.cigarorderservice.repositories.CustomerRepository;
import com.karalab.cigarorderservice.web.model.CigarOrderDto;
import com.karalab.cigarorderservice.web.model.CigarOrderLineDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
public class TastingRoomService {

    private final CustomerRepository customerRepository;
    private final CigarOrderService cigarOrderService;
    private final CigarOrderRepository cigarOrderRepository;
    private final List<String> cigarEans = new ArrayList<>(3);

    public TastingRoomService(CustomerRepository customerRepository, CigarOrderService cigarOrderService,
                              CigarOrderRepository cigarOrderRepository) {
        this.customerRepository = customerRepository;
        this.cigarOrderService = cigarOrderService;
        this.cigarOrderRepository = cigarOrderRepository;

        cigarEans.add(CigarOrderBootStrap.CIGAR_1_EAN);
        cigarEans.add(CigarOrderBootStrap.CIGAR_2_EAN);
        cigarEans.add(CigarOrderBootStrap.CIGAR_3_EAN);
    }

    @Transactional
    @Scheduled(fixedRate = 2000) //run every 2 seconds
    public void placeTastingRoomOrder(){

        List<Customer> customerList = customerRepository.findAllByCustomerNameLike(CigarOrderBootStrap.TASTING_ROOM);

        if (customerList.size() == 1){ //should be just one
            doPlaceOrder(customerList.get(0));
        } else {
            log.error("Too many or too few tasting room customers found");
        }
    }

    private void doPlaceOrder(Customer customer) {
        String cigarToOrder = getRandomCigarEan();

        CigarOrderLineDto cigarOrderLine = CigarOrderLineDto.builder()
                .ean(cigarToOrder)
                .orderQuantity(new Random().nextInt(6)) //todo externalize value to property
                .build();

        List<CigarOrderLineDto> cigarOrderLineSet = new ArrayList<>();
        cigarOrderLineSet.add(cigarOrderLine);

        CigarOrderDto cigarOrder = CigarOrderDto.builder()
                .customerId(customer.getId())
                .customerRef(UUID.randomUUID().toString())
                .cigarOrderLines(cigarOrderLineSet)
                .build();

        CigarOrderDto savedOrder = cigarOrderService.placeOrder(customer.getId(), cigarOrder);

    }

    private String getRandomCigarEan() {
        return cigarEans.get(new Random().nextInt(cigarEans.size() -0));
    }
}
