package com.karalab.cigarorderservice.web.mappers;

import com.karalab.cigarorderservice.domain.CigarOrderLine;
import com.karalab.cigarorderservice.web.model.CigarOrderDto;
import com.karalab.cigarorderservice.web.model.CigarOrderLineDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface CigarOrderLineMapper {

    CigarOrderLineDto cigarOrderLineToDto(CigarOrderLine line);

    CigarOrderLine dtoToCigarOrderLine(CigarOrderDto dto);
}
