package com.tra21.mapstruct_example.configs.properties;

import com.tra21.mapstruct_example.payloads.dtos.requests.PaginationRequestDto;
import com.tra21.mapstruct_example.utils.PaginationUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;

@Configuration
@ConfigurationProperties(value = "pagination")
@Data
public class PaginationConfiguration {
    private int defaultPage = 0;
    private int defaultSize = 10;
    private int maxSize = 100;
    public Pageable getPageable(PaginationRequestDto paginationRequestDto){
        int page = 0;
        int size = 10;
        if(paginationRequestDto.getPage() < 0){
            page = defaultPage;
        }
        if(paginationRequestDto.getSize() <= 0){
            size =  defaultSize;
        }
        if(paginationRequestDto.getSize() > maxSize){
            size = maxSize;
        }
        return PaginationUtils.getPageable(page, size);
    }
}
