package com.company.userregistrationapp.dao.repository;

import com.company.userregistrationapp.dao.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity,Long> {
    Page<TaskEntity> findAllByUserIdIs(Long userId, Pageable pageable);
}
