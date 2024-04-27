package com.company.userregistrationapp.controller;

import com.company.userregistrationapp.dto.request.ChangePasswordRequest;
import com.company.userregistrationapp.dto.request.SignInRequest;
import com.company.userregistrationapp.dto.request.SignUpRequest;
import com.company.userregistrationapp.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest request, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(userService.signUp(request, httpServletRequest));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInRequest request) {
        return ResponseEntity.ok(userService.signIn(request));
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@NotBlank String userName, HttpServletRequest request) {
        return ResponseEntity.ok(userService.forgotPassword(userName, request));
    }

    @GetMapping("/check-change-password")
    public ResponseEntity<?> checkChangePassword(@NotBlank @RequestBody String code) {
        return ResponseEntity.ok(userService.checkChangePassword(code));
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        return ResponseEntity.ok(userService.changePassword(request));

    }

    @GetMapping("/confirm-email")
    public ResponseEntity<?> confirmEmail(@RequestParam String code) {
        return ResponseEntity.ok(userService.confirmEmail(code));
    }


}
