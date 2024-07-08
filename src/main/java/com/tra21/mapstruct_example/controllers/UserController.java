package com.tra21.mapstruct_example.controllers;

import com.tra21.mapstruct_example.payloads.dtos.requests.PaginationRequestDto;
import com.tra21.mapstruct_example.payloads.dtos.requests.users.UserCreateRequestDto;
import com.tra21.mapstruct_example.payloads.dtos.requests.users.UserFilterRequestDto;
import com.tra21.mapstruct_example.payloads.dtos.requests.users.UserUpdateRequestDto;
import com.tra21.mapstruct_example.payloads.dtos.responses.PaginationResponseDto;
import com.tra21.mapstruct_example.payloads.dtos.responses.users.UserResponseDto;
import com.tra21.mapstruct_example.services.users.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    @GetMapping
    public ResponseEntity<PaginationResponseDto<UserResponseDto>> getAllPagination(PaginationRequestDto paginationRequestDto, UserFilterRequestDto userFilterRequestDto){
        return ResponseEntity.ok(userService.getAllPagination(paginationRequestDto, userFilterRequestDto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserCreateRequestDto userCreateRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userCreateRequestDto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable("id") Long id, @RequestBody @Valid UserUpdateRequestDto userUpdateRequestDto){
        return ResponseEntity.ok(userService.updateUser(id, userUpdateRequestDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.deleteSoftUser(id));
    }
}
