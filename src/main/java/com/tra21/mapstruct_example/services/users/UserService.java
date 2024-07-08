package com.tra21.mapstruct_example.services.users;

import com.tra21.mapstruct_example.configs.properties.PaginationConfiguration;
import com.tra21.mapstruct_example.mappers.AbstractUserMapper;
import com.tra21.mapstruct_example.mappers.IUserMapper;
import com.tra21.mapstruct_example.models.User;
import com.tra21.mapstruct_example.payloads.dtos.requests.PaginationRequestDto;
import com.tra21.mapstruct_example.payloads.dtos.requests.users.UserCreateRequestDto;
import com.tra21.mapstruct_example.payloads.dtos.requests.users.UserFilterRequestDto;
import com.tra21.mapstruct_example.payloads.dtos.requests.users.UserUpdateRequestDto;
import com.tra21.mapstruct_example.payloads.dtos.responses.PaginationResponseDto;
import com.tra21.mapstruct_example.payloads.dtos.responses.users.UserResponseDto;
import com.tra21.mapstruct_example.repositories.IUserRepository;
import com.tra21.mapstruct_example.specifications.users.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final PaginationConfiguration paginationConfiguration;
    private final IUserMapper userMapper;
    private final AbstractUserMapper abstractUserMapper;

    @Override
    public PaginationResponseDto<UserResponseDto> getAllPagination(PaginationRequestDto paginationRequestDto, UserFilterRequestDto userFilterRequestDto) {
        Page<User> userPage = userRepository.findAll(
                    UserSpecification.getAllFilter(userFilterRequestDto),
                    paginationConfiguration.getPageable(paginationRequestDto)
                );
        return userMapper.mapUserPagination(userPage);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        User user = getUserWithThrowException(id);
        return userMapper.mapUserResponse(user);
    }

    @Override
    public UserResponseDto createUser(UserCreateRequestDto userCreateRequestDto) {
        User user = abstractUserMapper.mapCreate(userCreateRequestDto);
        userRepository.save(user);
        return userMapper.mapUserResponse(user);
    }

    @Override
    public UserResponseDto updateUser(Long id, UserUpdateRequestDto userUpdateRequestDto) {
        User user = getUserWithThrowException(id);
        User userUpdate = abstractUserMapper.mapUpdate(user, userUpdateRequestDto);
        userRepository.save(userUpdate);
        return userMapper.mapUserResponse(userUpdate);
    }

    @Override
    public boolean deleteSoftUser(Long id) {
        User user = getUserWithThrowException(id);
        User userDelete = abstractUserMapper.mapDelete(user);
        userRepository.save(userDelete);
        return true;
    }
    private User getUserWithThrowException(Long id){
        return userRepository.findOne(UserSpecification.getById(id))
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }
}
