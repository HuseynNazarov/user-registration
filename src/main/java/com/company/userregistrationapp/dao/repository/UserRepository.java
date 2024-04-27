package com.company.userregistrationapp.dao.repository;

import com.company.userregistrationapp.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUserNameOrEmail(String userName,String email);
    Optional<UserEntity> findByUserName(String userName);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByConfirmCode(String confirmCode);
}
