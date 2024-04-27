package com.company.userregistrationapp.service;

import com.company.userregistrationapp.dto.request.ChangePasswordRequest;
import com.company.userregistrationapp.dto.request.SignInRequest;
import com.company.userregistrationapp.dto.request.SignUpRequest;
import com.company.userregistrationapp.dto.response.CommonResponse;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    CommonResponse<?> checkChangePassword(String code);

    CommonResponse<?> confirmEmail(String code);
    CommonResponse<?> forgotPassword(String userName, HttpServletRequest request);

    CommonResponse<?> changePassword(ChangePasswordRequest request);

    CommonResponse<?> signIn(SignInRequest request);

    CommonResponse<?> signUp(SignUpRequest request,HttpServletRequest httpServletRequest);
}
