package com.company.userregistrationapp.dto.response;

import java.io.Serializable;
import java.util.Objects;

public class CommonResponse<E> implements Serializable {
    private static final long serialVersionUID = 1L;
    private E data;
    private Status status;

    public CommonResponse() {
    }

    private CommonResponse(E data, Status status) {
        this.data = data;
        this.status = status;
    }

    public static <E> CommonResponse<E> of(E data, Status status) {
        return new CommonResponse<>(data, status);
    }

    public static <E> CommonResponse<E> of(Status status) {
        return new CommonResponse<>(null, status);
    }

    public static <E> CommonResponse<E> error(Status status) {

        return new CommonResponse<>(null, status);
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommonResponse<?> that = (CommonResponse<?>) o;
        return Objects.equals(data, that.data) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, status);
    }
}
