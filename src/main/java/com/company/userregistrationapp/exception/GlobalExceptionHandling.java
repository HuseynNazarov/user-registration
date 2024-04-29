package com.company.userregistrationapp.exception;

import com.company.userregistrationapp.dto.response.CommonResponse;
import com.company.userregistrationapp.dto.response.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonResponse<?> handleNotFoundException(NotFoundException ex) {

        return CommonResponse.error(new Status(ex.getCode(), ex.getMessage()));

    }

    @ExceptionHandler(InCorrectPasswordException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonResponse<?> handleInCorrectPasswordException(InCorrectPasswordException ex) {

        return CommonResponse.error(new Status(ex.getCode(), ex.getMessage()));

    }

    @ExceptionHandler(UserExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonResponse<?> handleUserExistException(UserExistException ex) {

        return CommonResponse.error(new Status(ex.getCode(), ex.getMessage()));

    }

    @ExceptionHandler(ConfirmPasswordException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonResponse<?> handleConfirmPasswordException(ConfirmPasswordException ex) {

        return CommonResponse.error(new Status(ex.getCode(), ex.getMessage()));

    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonResponse<?> handleUserNotFoundException(UserNotFoundException ex) {

        return CommonResponse.error(new Status(ex.getCode(), ex.getMessage()));

    }

    @ExceptionHandler(UserDisabledException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CommonResponse<?> handleUseDisabledException(UserDisabledException ex) {

        return CommonResponse.error(new Status(ex.getCode(), ex.getMessage()));

    }

    @ExceptionHandler(MailSendException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse<?> handleMailSendException(MailSendException ex) {

        return CommonResponse.error(new Status(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    private CommonResponse<?> handleBindingResult(BindingResult bindingResult) {
        List<String> errors = bindingResult
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        log.error("Validation Exception : {}", errors);

        return CommonResponse.error(
                Status.of(HttpStatus.BAD_REQUEST.value(), "Validation Error", String.join(", ", errors)));

    }

}
