package com.karalab.cigarorderservice.web.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CigarOrderDto extends BaseItem{

    private UUID customerId;
    private String customerRef;
    private List<CigarOrderLineDto> cigarOrderLines;
    private OrderStatusEnum orderStatus;
    private String orderStatusCallbackUrl;

    @Builder
    public CigarOrderDto(UUID id, int version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate,
                         UUID customerId, List<CigarOrderLineDto> cigarOrderLines, OrderStatusEnum orderStatus,
                         String orderStatusCallbackUrl, String customerRef) {
        super(id, version, createdDate, lastModifiedDate);
        this.customerId = customerId;
        this.cigarOrderLines = cigarOrderLines;
        this.orderStatus = orderStatus;
        this.orderStatusCallbackUrl = orderStatusCallbackUrl;
        this.customerRef = customerRef;
    }
}
