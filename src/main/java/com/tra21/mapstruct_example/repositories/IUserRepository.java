package com.tra21.mapstruct_example.repositories;

import com.tra21.mapstruct_example.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IUserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
}
