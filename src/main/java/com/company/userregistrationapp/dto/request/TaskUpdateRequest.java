package com.company.userregistrationapp.dto.request;

import com.company.userregistrationapp.dto.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskUpdateRequest {
    Long id;
    TaskStatus status;
    String name;
    String description;
    Long categoryId;
    Long projectId;
    Integer priority;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime deadLine;
}
