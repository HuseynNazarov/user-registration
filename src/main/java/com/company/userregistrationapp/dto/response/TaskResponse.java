package com.company.userregistrationapp.dto.response;

import com.company.userregistrationapp.enums.TaskStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskResponse {

    Long id;

    Long userId;

    String name;

    String description;

    Integer priority;

    LocalDateTime deadline;

    TaskStatus taskStatus;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

}
