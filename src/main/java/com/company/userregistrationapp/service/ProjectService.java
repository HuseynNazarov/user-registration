package com.company.userregistrationapp.service;

import com.company.userregistrationapp.dao.entity.ProjectEntity;

public interface ProjectService {
    ProjectEntity findById(Long id);
}
