package com.company.userregistrationapp.mapper;

import com.company.userregistrationapp.dao.entity.TaskEntity;
import com.company.userregistrationapp.dto.request.TaskSaveRequest;
import com.company.userregistrationapp.dto.request.TaskUpdateRequest;
import com.company.userregistrationapp.dto.response.TaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    TaskEntity convertToEntity(TaskUpdateRequest request);

    TaskEntity convertToEntity(TaskSaveRequest request);

    TaskResponse convertToResponse(TaskEntity taskEntity);


}
