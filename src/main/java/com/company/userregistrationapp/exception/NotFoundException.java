package com.company.userregistrationapp.exception;

import com.company.userregistrationapp.dto.enums.ExceptionEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotFoundException extends RuntimeException {
    int code;
    String message;

    public static NotFoundException of(ExceptionEnum exceptionEnum,Long id) {
        return new NotFoundException(exceptionEnum.getCode(),
                String.format(exceptionEnum.getMessage(),id));
    }

    public static NotFoundException of(ExceptionEnum exceptionEnum,String code) {
        return new NotFoundException(exceptionEnum.getCode(),
                String.format(exceptionEnum.getMessage(),code));
    }
}
