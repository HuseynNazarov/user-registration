package com.company.userregistrationapp.service.impl;

import com.company.userregistrationapp.config.security.SignedUserDetails;
import com.company.userregistrationapp.criteria.PageCriteria;
import com.company.userregistrationapp.criteria.SortingCriteria;
import com.company.userregistrationapp.dao.entity.CategoryEntity;
import com.company.userregistrationapp.dao.entity.ProjectEntity;
import com.company.userregistrationapp.dao.entity.TaskEntity;
import com.company.userregistrationapp.dao.repository.TaskRepository;
import com.company.userregistrationapp.dto.request.TaskSaveRequest;
import com.company.userregistrationapp.dto.request.TaskUpdateRequest;
import com.company.userregistrationapp.dto.response.CommonResponse;
import com.company.userregistrationapp.dto.response.SortingResponse;
import com.company.userregistrationapp.dto.response.Status;
import com.company.userregistrationapp.dto.response.TaskResponse;
import com.company.userregistrationapp.exception.NotFoundException;
import com.company.userregistrationapp.service.CategoryService;
import com.company.userregistrationapp.service.ProjectService;
import com.company.userregistrationapp.service.TaskService;
import com.company.userregistrationapp.utill.SortingUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.company.userregistrationapp.dto.enums.ExceptionEnum.TASK_NOT_FOUND;
import static com.company.userregistrationapp.dto.enums.TaskStatus.DELETED;
import static com.company.userregistrationapp.mapper.TaskMapper.INSTANCE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskServiceImpl implements TaskService {

    SortingUtil sortingUtil;
    TaskRepository taskRepository;
    UserServiceImpl userServiceImpl;
    CategoryService categoryService;
    ProjectService projectService;

    private Long getSignedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = ((SignedUserDetails) authentication.getPrincipal()).getUsername();

        return userServiceImpl.findByUserNameOrEmail(username).getId();

    }

    @Override
    public CommonResponse<?> getTasks(PageCriteria pageCriteria, SortingCriteria sortingCriteria) {
        Long userId = getSignedUserId();
        List<Sort.Order> sortOrder = sortingUtil.buildSortOrders(sortingCriteria);
        Page<TaskEntity> page = taskRepository.findAllByUserIdIs(userId, PageRequest.of(pageCriteria.getPage(), pageCriteria.getSize(), Sort.by(sortOrder)));

        SortingResponse response = new SortingResponse();
        response.setTotalPage(page.getTotalPages());
        response.setTotalElement(page.getTotalElements());
        response.setHasNext(page.hasNext());

        response.setTaskResponseList(INSTANCE.convertToResponseList(page.getContent()));
        return CommonResponse.of(response, Status.success());
    }

    @Override
    public CommonResponse<?> getTaskById(Long id) {
        TaskEntity entity = findTaskById(id);
        TaskResponse response = INSTANCE.convertToResponse(entity);
        return CommonResponse.of(response, Status.success());
    }

    @Override
    public CommonResponse<?> saveTask(TaskSaveRequest request) {

        Long userId = getSignedUserId();
        TaskEntity entity = INSTANCE.convertToEntity(request);
        entity.setUserId(userId);

        if (Objects.nonNull(request.getCategoryId())) {
            CategoryEntity category = categoryService.findById(request.getCategoryId());
            entity.setCategory(category);
        }

        if (Objects.nonNull(request.getProjectId())) {
            ProjectEntity project = projectService.findById(request.getProjectId());
            entity.setProject(project);
        }
        taskRepository.save(entity);
        return CommonResponse.of(Status.success());
    }

    @Override
    public CommonResponse<?> deleteTask(Long id) {
        TaskEntity entity = findTaskById(id);
        entity.setTaskStatus(DELETED);
        taskRepository.save(entity);

        return CommonResponse.of(Status.success());
    }

    @Override
    public CommonResponse<?> updateTaskWithStatus(TaskUpdateRequest request) {

        TaskEntity entity = findTaskById(request.getId());
        entity.setTaskStatus(request.getStatus());
        taskRepository.save(entity);

        return CommonResponse.of(Status.success());
    }

    @Override
    public CommonResponse<?> updateTask(TaskUpdateRequest request) {

        TaskEntity entity = findTaskById(request.getId());

        Optional.ofNullable(request.getStatus()).ifPresent(entity::setTaskStatus);
        Optional.ofNullable(request.getName()).ifPresent(entity::setName);
        Optional.ofNullable(request.getDescription()).ifPresent(entity::setDescription);
        Optional.ofNullable(request.getPriority()).ifPresent(entity::setPriority);
        Optional.ofNullable(request.getDeadLine()).ifPresent(entity::setDeadline);

        if (Objects.nonNull(request.getCategoryId())) {
            CategoryEntity category = categoryService.findById(request.getCategoryId());
            entity.setCategory(category);
        }

        if (Objects.nonNull(request.getProjectId())) {
            ProjectEntity project = projectService.findById(request.getProjectId());
            entity.setProject(project);
        }
        taskRepository.save(entity);

        return CommonResponse.of(Status.success());
    }

    public TaskEntity findTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() ->
                        NotFoundException.of(TASK_NOT_FOUND, id));
    }
}
