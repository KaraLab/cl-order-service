package com.karalab.cigarorderservice.web.mappers;

import com.karalab.cigarorderservice.domain.CigarOrder;
import com.karalab.cigarorderservice.web.model.CigarOrderDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class, CigarOrderLineMapper.class})
public interface CigarOrderMapper {

    CigarOrderDto cigarOrderToDto(CigarOrder cigarOrder);
    CigarOrder dtoToCigarOrder(CigarOrderDto dto);
}
