package com.karalab.cigarorderservice.repositories;

import com.karalab.cigarorderservice.domain.CigarOrderLine;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface CigarOrderLineRepository extends PagingAndSortingRepository<CigarOrderLine, UUID> {
}
