package com.company.userregistrationapp.criteria;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.USE_DEFAULTS;
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageCriteria {
    @JsonInclude(USE_DEFAULTS)
    Integer page=0;

    @JsonInclude(USE_DEFAULTS)
    Integer size=10;
}
