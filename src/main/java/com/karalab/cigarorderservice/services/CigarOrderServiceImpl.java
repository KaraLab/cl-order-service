package com.karalab.cigarorderservice.services;

import com.karalab.cigarorderservice.domain.CigarOrder;
import com.karalab.cigarorderservice.domain.Customer;
import com.karalab.cigarorderservice.domain.OrderStatusEnum;
import com.karalab.cigarorderservice.repositories.CigarOrderRepository;
import com.karalab.cigarorderservice.repositories.CustomerRepository;
import com.karalab.cigarorderservice.web.mappers.CigarOrderMapper;
import com.karalab.cigarorderservice.web.model.CigarOrderDto;
import com.karalab.cigarorderservice.web.model.CigarOrderPagedList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CigarOrderServiceImpl implements CigarOrderService{

    private final CigarOrderRepository cigarOrderRepository;
    private final CustomerRepository customerRepository;
    private final CigarOrderMapper cigarOrderMapper;
    private final ApplicationEventPublisher publisher;

    public CigarOrderServiceImpl(CigarOrderRepository cigarOrderRepository,
                                CustomerRepository customerRepository,
                                CigarOrderMapper cigarOrderMapper, ApplicationEventPublisher publisher) {
        this.cigarOrderRepository = cigarOrderRepository;
        this.customerRepository = customerRepository;
        this.cigarOrderMapper = cigarOrderMapper;
        this.publisher = publisher;
    }

    @Override
    public CigarOrderPagedList listOrders(UUID customerId, Pageable pageable) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isPresent()) {
            Page<CigarOrder> cigarOrderPage =
                    cigarOrderRepository.findAllByCustomer(customerOptional.get(), pageable);

            return new CigarOrderPagedList(cigarOrderPage
                    .stream()
                    .map(cigarOrderMapper::cigarOrderToDto)
                    .collect(Collectors.toList()), PageRequest.of(
                    cigarOrderPage.getPageable().getPageNumber(),
                    cigarOrderPage.getPageable().getPageSize()),
                    cigarOrderPage.getTotalElements());
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public CigarOrderDto placeOrder(UUID customerId, CigarOrderDto cigarOrderDto) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isPresent()) {
            CigarOrder cigarOrder = cigarOrderMapper.dtoToCigarOrder(cigarOrderDto);
            cigarOrder.setId(null); //should not be set by outside client
            cigarOrder.setCustomer(customerOptional.get());
            cigarOrder.setOrderStatus(OrderStatusEnum.NEW);

            cigarOrder.getCigarOrderLines().forEach(line -> line.setCigarOrder(cigarOrder));

            CigarOrder savedCigarOrder = cigarOrderRepository.saveAndFlush(cigarOrder);

            log.debug("Saved Cigar Order: " + cigarOrder.getId());

            //todo impl
            //  publisher.publishEvent(new NewCigarOrderEvent(savedCigarOrder));

            return cigarOrderMapper.cigarOrderToDto(savedCigarOrder);
        }
        //todo add exception type
        throw new RuntimeException("Customer Not Found");
    }

    @Override
    public CigarOrderDto getOrderById(UUID customerId, UUID orderId) {
        return cigarOrderMapper.cigarOrderToDto(getOrder(customerId, orderId));
    }

    @Override
    public void pickupOrder(UUID customerId, UUID orderId) {
        CigarOrder cigarOrder = getOrder(customerId, orderId);
        cigarOrder.setOrderStatus(OrderStatusEnum.PICKED_UP);

        cigarOrderRepository.save(cigarOrder);
    }

    private CigarOrder getOrder(UUID customerId, UUID orderId){
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if(customerOptional.isPresent()){
            Optional<CigarOrder> cigarOrderOptional = cigarOrderRepository.findById(orderId);

            if(cigarOrderOptional.isPresent()){
                CigarOrder cigarOrder = cigarOrderOptional.get();

                // fall to exception if customer id's do not match - order not for customer
                if(cigarOrder.getCustomer().getId().equals(customerId)){
                    return cigarOrder;
                }
            }
            throw new RuntimeException("Cigar Order Not Found");
        }
        throw new RuntimeException("Customer Not Found");
    }
}
