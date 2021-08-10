package com.karalab.cigarorderservice.repositories;

import com.karalab.cigarorderservice.domain.CigarOrder;
import com.karalab.cigarorderservice.domain.Customer;
import com.karalab.cigarorderservice.domain.OrderStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.UUID;

public interface CigarOrderRepository extends JpaRepository<CigarOrder, UUID> {

    Page<CigarOrder> findAllByCustomer(Customer customer, Pageable pageable);

    List<CigarOrder> findAllByOrderStatus(OrderStatusEnum orderStatusEnum);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    CigarOrder findOneById(UUID id);
}
