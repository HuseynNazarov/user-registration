package com.company.userregistrationapp.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskSaveRequest {

    @NotBlank(message = "Name should not be null")
    String name;

    String description;

    Long categoryId;

    Long projectId;

    Integer priority;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime deadLine;
}
