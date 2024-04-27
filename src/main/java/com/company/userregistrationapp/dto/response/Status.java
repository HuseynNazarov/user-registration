package com.company.userregistrationapp.dto.response;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class Status implements Serializable {
    private static final long serialVersionUID = 1L;
    private int code = HttpStatus.OK.value();
    private String description = "success";
    private String message = "success";
    private static final Status SUCCESS_STATUS = new Status(HttpStatus.OK.value(), "success");

    public Status() {
    }

    public Status(int code, String description) {
        this.code = code;
        this.description = description;
    }

    private Status(int code, String description, String message) {
        this.code=code;
        this.description=description;
        this.message=message;
    }


    public static Status success() {
        return Status.SUCCESS_STATUS;
    }

    public static Status of(int code, String description, String message) {
        return new Status(code, description, message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
