package com.karalab.cigarorderservice.web.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class CigarOrderPagedList extends PageImpl<CigarOrderDto> {

    public CigarOrderPagedList(List<CigarOrderDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public CigarOrderPagedList(List<CigarOrderDto> content) {
        super(content);
    }
}
