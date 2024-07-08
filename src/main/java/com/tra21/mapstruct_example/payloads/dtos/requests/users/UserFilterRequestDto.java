package com.tra21.mapstruct_example.payloads.dtos.requests.users;

import com.tra21.mapstruct_example.payloads.enums.UserStatus;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserFilterRequestDto implements Serializable {
    private String search;
    private int ageFrom;
    private int ageTo;
    private UserStatus userStatus;
}
