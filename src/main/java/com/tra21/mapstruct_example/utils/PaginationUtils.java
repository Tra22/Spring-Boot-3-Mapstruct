package com.tra21.mapstruct_example.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PaginationUtils {
    public static Pageable getPageable(int page, int size){
        return PageRequest.of(page, size);
    }
}
