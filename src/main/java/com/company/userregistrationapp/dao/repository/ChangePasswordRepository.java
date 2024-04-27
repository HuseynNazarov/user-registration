package com.company.userregistrationapp.dao.repository;

import com.company.userregistrationapp.dao.entity.ChangePasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ChangePasswordRepository extends JpaRepository<ChangePasswordEntity,Long> {
    Optional<ChangePasswordEntity> findByCodeAndExpiredTimeAfter(String code, LocalDateTime now);
}
