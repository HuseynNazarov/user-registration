package com.company.userregistrationapp.service.impl;

import com.company.userregistrationapp.config.security.TokenManager;
import com.company.userregistrationapp.dao.entity.ChangePasswordEntity;
import com.company.userregistrationapp.dao.entity.UserEntity;
import com.company.userregistrationapp.dao.repository.ChangePasswordRepository;
import com.company.userregistrationapp.dao.repository.UserRepository;
import com.company.userregistrationapp.dto.request.ChangePasswordRequest;
import com.company.userregistrationapp.dto.request.SignInRequest;
import com.company.userregistrationapp.dto.request.SignUpRequest;
import com.company.userregistrationapp.dto.response.CommonResponse;
import com.company.userregistrationapp.dto.response.MailDto;
import com.company.userregistrationapp.dto.response.SignInResponse;
import com.company.userregistrationapp.dto.response.Status;
import com.company.userregistrationapp.exception.*;
import com.company.userregistrationapp.service.UserService;
import com.company.userregistrationapp.utill.MailSenderUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.company.userregistrationapp.dto.enums.ExceptionEnum.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    ChangePasswordRepository changePasswordRepository;

    MailSenderUtil mailSenderUtil;

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    AuthenticationManager authenticationManager;

    TokenManager tokenManager;

    public ChangePasswordEntity findByCodeAndExpiredTimeAfter(String code) {
        return changePasswordRepository.findByCodeAndExpiredTimeAfter(code, LocalDateTime.now())
                .orElseThrow(() ->
                        NotFoundException.of(RESET_CODE_NOT_FOUND, code));
    }

    public UserEntity findByConfirmCode(String code) {
        return userRepository.findByConfirmCode(code)
                .orElseThrow(() -> NotFoundException.of(CONFIRM_CODE_NOT_FOUND, code));
    }

    public UserEntity findByUserNameOrEmail(String userName) {
        return userRepository.findByUserNameOrEmail(userName, userName).orElseThrow(() ->
                UserNotFoundException.of(USER_NOT_EXISTS));
    }

    public UserEntity findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                UserNotFoundException.of(USER_NOT_EXISTS));
    }

    @Override
    public CommonResponse<?> checkChangePassword(String code) {
        findByCodeAndExpiredTimeAfter(code);
        return CommonResponse.of(Status.success());
    }

    @Override
    public CommonResponse<?> confirmEmail(String code) {
        UserEntity entity = findByConfirmCode(code);
        entity.setIsEnabled(true);
        userRepository.save(entity);

        return CommonResponse.of(Status.success());
    }

    @Override
    public CommonResponse<?> forgotPassword(String userName, HttpServletRequest request) {

        UserEntity userEntity = findByUserNameOrEmail(userName);

        if (!userEntity.isEnabled()) {
            throw UserDisabledException.of(EMAIL_IS_NOT_CONFIRMED);
        }

        ChangePasswordEntity changePasswordEntity = ChangePasswordEntity
                .builder()
                .userId(userEntity.getId())
                .code(UUID.randomUUID().toString())
                .expiredTime(LocalDateTime.now().plusMinutes(30))
                .build();
        changePasswordRepository.save(changePasswordEntity);


        try {
            MailDto mailDto = MailDto.builder()
                    .toAddress(userEntity.getEmail())
                    .subject("Reset your password")
                    .content("Please click the link below to reset your password:<br>" +
                            "<h3><a href=" + getSiteURL(request) + "/v1/user/change-password?code=" + changePasswordEntity.getCode() + " target='_self'>Reset</a></h3>" +
                            "Thank you<br>")
                    .build();
            mailSenderUtil.sendMail(mailDto);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new MailSendException(504, "Error in sending mail");
        }
        return CommonResponse.of(Status.success());
    }

    @Override
    public CommonResponse<?> changePassword(ChangePasswordRequest request) {
        ChangePasswordEntity changePassword = findByCodeAndExpiredTimeAfter(request.getCode());

        if (request.getPassword().equals(request.getConfirmPassword())) {
            throw ConfirmPasswordException.of(PASSWORD_NOT_EQUAL_CONFIRM_PASSWORD);
        }
        UserEntity userEntity = findById(changePassword.getUserId());

        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(userEntity);
        changePasswordRepository.delete(changePassword);

        return CommonResponse.of(Status.success());
    }

    @Override
    public CommonResponse<?> signIn(SignInRequest request) {
        UserEntity userEntity = findByUserNameOrEmail(request.getUsername());

        if (!userEntity.isEnabled()) {
            throw UserDisabledException.of(EMAIL_IS_NOT_CONFIRMED);
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        String token = tokenManager.generateToken(request.getUsername());

        SignInResponse signInResponse = new SignInResponse();
        signInResponse.setAccessToken(token);

        return CommonResponse.of(signInResponse, Status.success());
    }

    @Override
    public CommonResponse<?> signUp(SignUpRequest request, HttpServletRequest httpServletRequest) {
        if (userRepository.findByUserNameOrEmail(request.getUsername(), request.getEmail()).isPresent()) {
            throw new UserExistException(USER_ALREADY_EXISTS.getCode(),
                    USER_ALREADY_EXISTS.getMessage());
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw ConfirmPasswordException.of(PASSWORD_NOT_EQUAL_CONFIRM_PASSWORD);
        }

        UserEntity entity = new UserEntity();
        entity.setUserName(request.getUsername());
        entity.setEmail(request.getEmail());
        entity.setConfirmCode(UUID.randomUUID().toString());
        entity.setPassword(passwordEncoder.encode(request.getPassword()));
        entity.setIsEnabled(false);
        userRepository.save(entity);

        try {
            MailDto mailDto = MailDto.builder()
                    .toAddress(entity.getEmail())
                    .subject("Confirm your mail")
                    .content("Dear " + entity.getUsername() +
                            ",<br> Please click the link below to confirm your registration:<br>" +
                            "<h3><a href=" + getSiteURL(httpServletRequest) +
                            "/v1/user/confirm-mail?code=" + entity.getConfirmCode() +
                            " target='_self'>Confirm</a></h3>" +
                            "Thank you<br>")
                    .build();
            mailSenderUtil.sendMail(mailDto);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new MailSendException(504, "Error in sending mail");
        }
        return CommonResponse.of(Status.success());
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
