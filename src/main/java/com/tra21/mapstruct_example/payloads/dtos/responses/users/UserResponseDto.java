package com.tra21.mapstruct_example.payloads.dtos.responses.users;

import com.tra21.mapstruct_example.payloads.dtos.responses.AuditResponseDto;
import com.tra21.mapstruct_example.payloads.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDto extends AuditResponseDto implements Serializable {
    private Long userId;
    private String fullName;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private int age;
    private Date birthDate;
    private String profile;
    private UserStatus status;
}
