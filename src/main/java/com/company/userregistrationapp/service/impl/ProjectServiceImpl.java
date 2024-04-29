package com.company.userregistrationapp.service.impl;

import com.company.userregistrationapp.dao.entity.ProjectEntity;
import com.company.userregistrationapp.dao.repository.ProjectRepository;
import com.company.userregistrationapp.exception.NotFoundException;
import com.company.userregistrationapp.service.ProjectService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import static com.company.userregistrationapp.enums.ExceptionEnum.PROJECT_NOT_FOUND;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectServiceImpl implements ProjectService {

    ProjectRepository projectRepository;

    @Override
    public ProjectEntity findById(Long id) {
        return projectRepository
                .findById(id)
                .orElseThrow(() ->
                        NotFoundException.of(PROJECT_NOT_FOUND.getCode(),
                                String.format(PROJECT_NOT_FOUND.getMessage(), id)));

    }
}
