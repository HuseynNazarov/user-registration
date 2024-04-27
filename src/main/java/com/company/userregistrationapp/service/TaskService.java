package com.company.userregistrationapp.service;

import com.company.userregistrationapp.criteria.PageCriteria;
import com.company.userregistrationapp.criteria.SortingCriteria;
import com.company.userregistrationapp.dto.request.TaskSaveRequest;
import com.company.userregistrationapp.dto.request.TaskUpdateRequest;
import com.company.userregistrationapp.dto.response.CommonResponse;

public interface TaskService {
    CommonResponse<?> getTasks(PageCriteria pageCriteria, SortingCriteria sortingCriteria);

    CommonResponse<?> getTaskById(Long id);

    CommonResponse<?> saveTask(TaskSaveRequest request);

    CommonResponse<?> deleteTask(Long id);

    CommonResponse<?> updateTaskWithStatus(TaskUpdateRequest request);

    CommonResponse<?> updateTask(TaskUpdateRequest request);
}
