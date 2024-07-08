package com.tra21.mapstruct_example.payloads.dtos.responses;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationResponseDto<T> implements Serializable {
    private int page;
    private int size;
    private Long totalElements;
    private int totalPages;
    private List<T> data = new ArrayList<>();
}
