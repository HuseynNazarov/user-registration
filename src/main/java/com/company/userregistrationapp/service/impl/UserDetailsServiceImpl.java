package com.company.userregistrationapp.service.impl;

import com.company.userregistrationapp.dao.repository.UserRepository;
import com.company.userregistrationapp.exception.UserExistException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUserNameOrEmail(username, username)
                .orElseThrow(() -> new UserExistException(404, "User with username or email already exists"));
    }

//    public UserEntity signedUser(String username) {
//        return userRepository.findByUserName(username)
//                .orElseThrow(() -> new UserExistException(404,"User with username or email already exists"));
//}

}
