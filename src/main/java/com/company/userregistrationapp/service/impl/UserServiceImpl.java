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

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.UUID;

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

    @Override
    public CommonResponse<?> checkChangePassword(String code) {
        changePasswordRepository.findByCodeAndExpiredTimeAfter(code, LocalDateTime.now())
                .orElseThrow(() ->
                        new NotFoundException(404,
                                String.format("This reset password code %s was not found or code time is expired", code)));

        return CommonResponse.of(Status.success());
    }

    @Override
    public CommonResponse<?> confirmEmail(String code) {
        UserEntity entity = userRepository.findByConfirmCode(code)
                .orElseThrow(() -> new NotFoundException(404,
                        String.format("Confirm code %s was not found", code)));
        entity.setIsEnabled(true);
        userRepository.save(entity);

        return CommonResponse.of(Status.success());
    }

    @Override
    public CommonResponse<?> forgotPassword(String userName, HttpServletRequest request) {

        UserEntity userEntity = userRepository.findByUserNameOrEmail(userName, userName).orElseThrow(() ->
                new UserNotFoundException(404, "User name or email not exist"));

        if (!userEntity.isEnabled()) {
            throw new UserDisabledException(HttpStatus.UNAUTHORIZED.value(), "Mail is not confirmed");
        }

        ChangePasswordEntity changePasswordEntity = ChangePasswordEntity.builder()
                .userId(userEntity.getId())
                .code(UUID.randomUUID().toString())
                .expiredTime(LocalDateTime.now().plusMinutes(30))
                .build();
        changePasswordRepository.save(changePasswordEntity);


//        try {
//            MailDto mailDto = MailDto.builder()
//                    .toAddress(userEntity.getEmail())
//                    .subject("Reset your password")
//                    .content("Please click the link below to reset your password:<br>" +
//                            "<h3><a href=" + getSiteURL(request) + "/v1/user/change-password?code=" + changePasswordEntity.getCode() + " target='_self'>Reset</a></h3>" +
//                            "Thank you<br>")
//                    .build();
//            mailSenderUtil.sendMail(mailDto);
//        } catch (MessagingException | UnsupportedEncodingException e) {
//            throw new MailSendException(504, "Error in sending mail");
//        }
        return CommonResponse.of(Status.success());
    }

    @Override
    public CommonResponse<?> changePassword(ChangePasswordRequest request) {
        ChangePasswordEntity changePassword = changePasswordRepository.findByCodeAndExpiredTimeAfter(request.getCode(), LocalDateTime.now())
                .orElseThrow(() ->
                        new NotFoundException(404,
                                String.format("This reset password code %s was not found or code time is expired", request.getCode())));
        if (request.getPassword().equals(request.getConfirmPassword())) {
            throw new ConfirmPasswordException(404, "password not equals confirm password");
        }
        UserEntity userEntity = userRepository.findById(changePassword.getUserId()).orElseThrow(() ->
                new UserNotFoundException(404,
                        String.format("User not exist", request.getCode())));

        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(userEntity);
        changePasswordRepository.delete(changePassword);

        return CommonResponse.of(Status.success());
    }

    @Override
    public CommonResponse<?> signIn(SignInRequest request) {
        UserEntity userEntity = userRepository.findByUserNameOrEmail(request.getUsername(), request.getUsername())
                .orElseThrow(() ->
                        new UserNotFoundException(404,
                                String.format("User %s not exist", request.getUsername())));
        ;
        if (!userEntity.isEnabled()) {
            throw new UserDisabledException(404, "user email is not confirmed");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        String token = tokenManager.generateToken(request.getUsername());

        SignInResponse signInResponse = new SignInResponse();
        signInResponse.setAccessToken(token);

        return CommonResponse.of(signInResponse,Status.success());
    }

    @Override
    public CommonResponse<?> signUp(SignUpRequest request, HttpServletRequest httpServletRequest) {
        if (userRepository.findByUserNameOrEmail(request.getUsername(), request.getEmail()).isPresent()) {
            throw new UserExistException(404, "User already exist");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new ConfirmPasswordException(404, "password not equals confirm password");
        }

        UserEntity entity = new UserEntity();
        entity.setUserName(request.getUsername());
        entity.setEmail(request.getEmail());
        entity.setConfirmCode(UUID.randomUUID().toString());
        entity.setPassword(passwordEncoder.encode(request.getPassword()));
        entity.setIsEnabled(false);
        userRepository.save(entity);

//        try {
//            MailDto mailDto = MailDto.builder()
//                    .toAddress(entity.getEmail())
//                    .subject("Confirm your mail")
//                    .content("Dear "+ entity.getUsername()+
//                            ",<br> Please click the link below to confirm your registration:<br>" +
//                            "<h3><a href="+ getSiteURL(httpServletRequest) +
//                            "/v1/user/confirm-mail?code=" + entity.getConfirmCode()+
//                            " target='_self'>Confirm</a></h3>" +
//                            "Thank you<br>")
//         .build();
//            mailSenderUtil.sendMail(mailDto);
//        } catch (MessagingException | UnsupportedEncodingException e) {
//            throw new MailSendException(504, "Error in sending mail");
//        }
        return CommonResponse.of(Status.success());
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
