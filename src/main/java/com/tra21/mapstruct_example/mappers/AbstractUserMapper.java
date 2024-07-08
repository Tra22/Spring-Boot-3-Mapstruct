package com.tra21.mapstruct_example.mappers;

import com.tra21.mapstruct_example.models.User;
import com.tra21.mapstruct_example.payloads.dtos.requests.users.UserCreateRequestDto;
import com.tra21.mapstruct_example.payloads.dtos.requests.users.UserUpdateRequestDto;
import com.tra21.mapstruct_example.payloads.enums.UserStatus;
import com.tra21.mapstruct_example.repositories.IUserRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

@Mapper(componentModel = "spring", imports = { UserStatus.class, LocalDateTime.class }, uses = { IUserMapper.class })
public abstract class AbstractUserMapper {
    @Autowired
    protected IUserRepository userRepository;

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "status", expression = "java(UserStatus.ACTIVE)"),
            @Mapping(target = "createdDate", expression = "java(LocalDateTime.now())"),
            @Mapping(target = "createdBy", ignore = true),
            @Mapping(target = "lastModifiedDate", ignore = true),
            @Mapping(target = "lastModifiedBy", ignore = true)
    })
    public abstract User mapCreate(UserCreateRequestDto userCreateRequestDto);
    @BeanMapping(ignoreByDefault = true)
    @Mappings({
            @Mapping(target = "firstName", source = "firstName"),
            @Mapping(target = "middleName", source = "middleName"),
            @Mapping(target = "lastName", source = "lastName"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "birthDate", source = "birthDate"),
            @Mapping(target = "lastModifiedDate", expression = "java(LocalDateTime.now())"),
            @Mapping(target = "lastModifiedBy", ignore = true)
    })
    public abstract User mapUpdate(@MappingTarget User user, UserUpdateRequestDto userUpdateRequestDto);
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "status", source = "status", qualifiedByName = "deleteUser")
    public abstract User mapDelete(User user);
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "status", source = ".")
    public abstract User mapUpdateUserStatus(@MappingTarget User user, UserStatus userStatus);
    @Named("deleteUser")
    protected UserStatus deleteUser(UserStatus userStatus){
        return userStatus.equals(UserStatus.DELETED) ? userStatus :  UserStatus.DELETED;
    }
    //just example before mapping
    @BeforeMapping
    protected void mapUserCreateBefore(UserCreateRequestDto userCreateRequestDto, @MappingTarget User user){
        Optional<User> userOptional = userRepository.findById(1L);
        userOptional.ifPresent(user::setCreatedBy);
    }
    //just example after mapping
    @AfterMapping
    protected void mapUserUpdateAfter(UserUpdateRequestDto userUpdateRequestDto, @MappingTarget User user){
        Optional<User> userOptional = userRepository.findById(1L);
        userOptional.ifPresent(user::setLastModifiedBy);
    }
}
