package com.tra21.mapstruct_example.payloads.dtos.requests;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaginationRequestDto implements Serializable {
    private int page;
    private int size;
}
