package com.karalab.cigarorderservice.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class CigarOrder extends BaseEntity {

    private String customerRef;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "cigarOrder", cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private Set<CigarOrderLine> cigarOrderLines;

    private OrderStatusEnum orderStatus = OrderStatusEnum.NEW;
    private String orderStatusCallbackUrl;

    @Builder
    public CigarOrder(UUID id, Long version, Timestamp createdDate, Timestamp lastModifiedDate, String customerRef,
                      Customer customer, Set<CigarOrderLine> beerOrderLines, OrderStatusEnum orderStatus,
                      String orderStatusCallbackUrl) {
        super(id, version, createdDate, lastModifiedDate);
        this.customerRef = customerRef;
        this.customer = customer;
        this.cigarOrderLines = beerOrderLines;
        this.orderStatus = orderStatus;
        this.orderStatusCallbackUrl = orderStatusCallbackUrl;
    }
}
