package com.company.userregistrationapp.dao.repository;

import com.company.userregistrationapp.dao.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity,Long> {
}
