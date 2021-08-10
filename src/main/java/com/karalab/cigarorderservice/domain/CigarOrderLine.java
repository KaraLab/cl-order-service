package com.karalab.cigarorderservice.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CigarOrderLine extends BaseEntity {

    @ManyToOne
    private CigarOrder cigarOrder;

    private UUID cigarId;
    private int orderQuantity = 0;
    private int quantityAllocated = 0;

    @Builder
    public CigarOrderLine(UUID id, long version, Timestamp createdDate, Timestamp lastModifiedDate,
                          CigarOrder cigarOrder, UUID cigarId, int orderQuantity, int quantityAllocated) {
        super(id, version, createdDate, lastModifiedDate);
        this.cigarOrder = cigarOrder;
        this.cigarId = cigarId;
        this.orderQuantity = orderQuantity;
        this.quantityAllocated = quantityAllocated;
    }
}
