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
public class CustomerDto extends BaseItem{

    private String name;

    @Builder
    public CustomerDto(UUID id, int version, OffsetDateTime createdDateTime,
                       OffsetDateTime lastModifiedDate, String name) {
        super(id, version, createdDateTime, lastModifiedDate);
        this.name = name;
    }
}
