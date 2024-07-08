package com.tra21.mapstruct_example.payloads.dtos.responses;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AuditResponseDto implements Serializable {
    private String createdBy;
    private Date createdAt;
    private String updatedBy;
    private Date updatedAt;
}
