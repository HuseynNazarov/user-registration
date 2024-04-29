package com.company.userregistrationapp.enums;

import lombok.Getter;

@Getter
public enum ExceptionEnum {

    CATEGORY_NOT_FOUND(101, "Category with id %s was not found!"),

    PROJECT_NOT_FOUND(102,"Project with id %s was not found!"),

    USER_ALREADY_EXISTS(103,"User with username or email already exists"),

    USER_NOT_EXISTS(104,"User with username or email not exists"),

    TASK_NOT_FOUND(105,"Task with id %s was not found!"),

    CONFIRM_CODE_NOT_FOUND(106,"Confirm code %s was not found"),

    PASSWORD_NOT_EQUAL_CONFIRM_PASSWORD(107,"Password not equals confirm password"),

    EMAIL_IS_NOT_CONFIRMED(108,"User email is not confirmed"),

    RESET_CODE_NOT_FOUND(109,"This reset password code %s was not found or code time is expired");

    private final int code;
    private final String message;

    ExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
