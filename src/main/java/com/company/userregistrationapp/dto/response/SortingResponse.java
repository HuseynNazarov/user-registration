package com.company.userregistrationapp.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SortingResponse {
    int totalPage;
    long totalElement;
    List<TaskResponse> taskResponseList;
}
