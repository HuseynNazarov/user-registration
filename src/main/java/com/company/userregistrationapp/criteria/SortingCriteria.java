package com.company.userregistrationapp.criteria;

import com.company.userregistrationapp.dto.enums.SortEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SortingCriteria {
    SortEnum createdAt;

    SortEnum name;

    SortEnum priority;

    SortEnum deadline;

}
