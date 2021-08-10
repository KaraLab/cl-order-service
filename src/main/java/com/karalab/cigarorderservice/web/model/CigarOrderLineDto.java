package com.karalab.cigarorderservice.web.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CigarOrderLineDto extends BaseItem{

    private String ean;
    private String cigarName;
    private UUID cigarId;
    private int orderQuantity = 0;

    @Builder
    public CigarOrderLineDto(UUID id, int version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate,
                             String ean, String cigarName, UUID cigarId, int orderQuantity) {
        super(id, version, createdDate, lastModifiedDate);
        this.ean = ean;
        this.cigarName = cigarName;
        this.cigarId = cigarId;
        this.orderQuantity = orderQuantity;
    }
}
