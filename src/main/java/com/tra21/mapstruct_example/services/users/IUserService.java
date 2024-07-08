package com.tra21.mapstruct_example.services.users;

import com.tra21.mapstruct_example.payloads.dtos.requests.PaginationRequestDto;
import com.tra21.mapstruct_example.payloads.dtos.requests.users.UserCreateRequestDto;
import com.tra21.mapstruct_example.payloads.dtos.requests.users.UserFilterRequestDto;
import com.tra21.mapstruct_example.payloads.dtos.requests.users.UserUpdateRequestDto;
import com.tra21.mapstruct_example.payloads.dtos.responses.PaginationResponseDto;
import com.tra21.mapstruct_example.payloads.dtos.responses.users.UserResponseDto;

public interface IUserService {
    PaginationResponseDto<UserResponseDto> getAllPagination(PaginationRequestDto paginationRequestDto, UserFilterRequestDto userFilterRequestDto);
    UserResponseDto getUserById(Long id);
    UserResponseDto createUser(UserCreateRequestDto userCreateRequestDto);
    UserResponseDto updateUser(Long id, UserUpdateRequestDto userUpdateRequestDto);
    boolean deleteSoftUser(Long id);
}
