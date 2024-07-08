package com.tra21.mapstruct_example.specifications.users;

import com.tra21.mapstruct_example.models.User;
import com.tra21.mapstruct_example.models.User_;
import com.tra21.mapstruct_example.payloads.dtos.requests.users.UserFilterRequestDto;
import com.tra21.mapstruct_example.payloads.enums.UserStatus;
import com.tra21.mapstruct_example.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.Date;

@Slf4j
public class UserSpecification {
    public static Specification<User> getAllFilter(UserFilterRequestDto userFilterRequestDto){
        String search = StringUtils.hasText(userFilterRequestDto.getSearch()) ? userFilterRequestDto.getSearch().toLowerCase() : null;
        return (root, query, builder) ->
                builder.and(
                        getSearchFilter(search).toPredicate(root, query, builder),
                        getAgeFilter(userFilterRequestDto.getAgeFrom(), userFilterRequestDto.getAgeTo()).toPredicate(root, query, builder),
                        getUserStatusFilter(userFilterRequestDto.getUserStatus()).toPredicate(root, query, builder)
                );
    }
    public static Specification<User> getById(Long id) {
        return (root, query, builder) -> builder.equal(root.get(User_.ID), id);
    }
    public static Specification<User> getByIdBaseOnStatus(Long id, UserStatus userStatus){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                    getById(id).toPredicate(root, query, criteriaBuilder),
                    criteriaBuilder.equal(root.get(User_.STATUS), userStatus)
                );
    }
    private static Specification<User> getSearchFilter(String search){
        return (root, query, builder) ->
                search == null ?
                        builder.conjunction() :
                        builder.or(
                                builder.like(builder.lower(root.get(User_.FIRST_NAME)), generateSearchWithLike(search)),
                                builder.like(builder.lower(root.get(User_.MIDDLE_NAME)), generateSearchWithLike(search)),
                                builder.like(builder.lower(root.get(User_.LAST_NAME)), generateSearchWithLike(search)),
                                builder.like(builder.lower(root.get(User_.EMAIL)), generateSearchWithLike(search))
                        );
    }
    private static String generateSearchWithLike(String search){
        if(StringUtils.hasText(search)){
            return MessageFormatter.arrayFormat("{}{}{}", new Object[]{
                    "%",
                    search,
                    "%"
            }).getMessage();
        }
        return search;
    }
    private static Specification<User> getAgeFilter(int ageFrom, int ageTo){
        return (root, query, builder) -> {
            if(ageFrom <= 0 && ageTo <= 0){
                return builder.conjunction();
            }else{
                int ageF = Math.max(ageFrom, 0);
                int ageT = ageTo <= 0 ? 200 : ageTo;
                Date dateFrom = UserUtils.getAgeAsDate(ageF, true);
                Date dateTo = UserUtils.getAgeAsDate(ageT, false);
                log.info("dateFrom: {}, dateTo: {}", dateFrom, dateTo);
                return builder.and(
                    builder.greaterThanOrEqualTo(root.get(User_.BIRTH_DATE), dateTo),
                    builder.lessThanOrEqualTo(root.get(User_.BIRTH_DATE), dateFrom)
                );
            }
        };
    }
    private static Specification<User> getUserStatusFilter(UserStatus userStatus){
        return (root, query, criteriaBuilder) ->
            userStatus == null ?
                criteriaBuilder.conjunction() :
                criteriaBuilder.equal(root.get(User_.STATUS), userStatus);
    }
}
