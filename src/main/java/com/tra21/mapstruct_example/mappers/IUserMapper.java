package com.tra21.mapstruct_example.mappers;

import com.tra21.mapstruct_example.models.User;
import com.tra21.mapstruct_example.payloads.dtos.responses.PaginationResponseDto;
import com.tra21.mapstruct_example.payloads.dtos.responses.users.UserResponseDto;
import com.tra21.mapstruct_example.utils.DateUtils;
import com.tra21.mapstruct_example.utils.UserUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Mapper(componentModel = "spring", imports = { UserUtils.class, LocalDateTime.class })
public interface IUserMapper {

    @Mappings({
            @Mapping(target = "data", source = "content"),
            @Mapping(target = "page", source = "pageable.pageNumber"),
            @Mapping(target = "size", source = "pageable.pageSize"),
            @Mapping(target = "totalElements", source = "totalElements"),
            @Mapping(target = "totalPages", source = "totalPages")
    })
    PaginationResponseDto<UserResponseDto> mapUserPagination(Page<User> userPage);
    @Mappings({
        @Mapping(target = "userId", source = "id"),
        @Mapping(target = "age", expression = "java(UserUtils.getAge(user.getBirthDate()))"),
        @Mapping(target = "fullName", source = ".", qualifiedByName = "getFullName"),
        @Mapping(target = "updatedBy", source = "lastModifiedBy", qualifiedByName = "optionalUserMap"),
        @Mapping(target = "createdBy", source = "createdBy", qualifiedByName = "optionalUserMap"),
        @Mapping(target = "createdAt", ignore = true),
        @Mapping(target = "updatedAt", ignore = true)
    })
    UserResponseDto mapUserResponse(User user);
    @AfterMapping
    default void afterUserMapping(User user, @MappingTarget UserResponseDto userResponseDto){
        if(user.getCreatedDate().isPresent()){
            userResponseDto.setCreatedAt(DateUtils.convertLocalDateToDate(user.getCreatedDate().get()));
        }
        if(user.getLastModifiedDate().isPresent()){
            userResponseDto.setUpdatedAt(DateUtils.convertLocalDateToDate(user.getLastModifiedDate().get()));
        }
    }
    @Named("getFullName")
    default String getFullName(User user){
        if(StringUtils.hasText(user.getMiddleName())){
            return  MessageFormatter.arrayFormat(
                    "{} {} {}",
                    new Object[]{
                            user.getLastName(),
                            user.getMiddleName(),
                            user.getFirstName()
                    }
            ).getMessage();
        }
        return MessageFormatter.arrayFormat(
                "{} {}",
                new Object[]{
                    user.getLastName(),
                    user.getFirstName()
                }
        ).getMessage();
    }
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @Named("optionalUserMap")
    default String optionalUserMap(Optional<User> userOptional){
        return userOptional.map(this::getFullName).orElse(null);
    }
}
