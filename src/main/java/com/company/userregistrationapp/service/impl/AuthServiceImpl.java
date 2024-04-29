package com.company.userregistrationapp.service.impl;

import com.company.userregistrationapp.config.security.SignedUserDetails;
import com.company.userregistrationapp.config.security.TokenManager;
import com.company.userregistrationapp.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.company.userregistrationapp.enums.AuthEnum.AUTH_HEADER;
import static com.company.userregistrationapp.enums.AuthEnum.BEARER_AUTH_HEADER;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {

    TokenManager tokenManager;

    @Override
    public Optional<Authentication> getAuthentication(HttpServletRequest httpServletRequest) {
        return Optional.ofNullable(httpServletRequest.getHeader(AUTH_HEADER))
                .filter(this::isBearerAuth)
                .flatMap(this::getAuthenticationBearer);
    }

    private boolean isBearerAuth(String header) {
        return header.toLowerCase().startsWith(BEARER_AUTH_HEADER.toLowerCase());
    }

    private Optional<Authentication> getAuthenticationBearer(String header) {
        String token = header.substring(BEARER_AUTH_HEADER.length()).trim();

        if (tokenManager.tokenValidate(token)) {
            return Optional.empty();
        }

        String username = tokenManager.getUserFromToken(token);

        return Optional.of(getAuthentication(username));
    }

    private Authentication getAuthentication(String userName) {

        SignedUserDetails signedUserDetails = SignedUserDetails
                .builder()
                .username(userName)
                .email(userName)
                .build();

        return new UsernamePasswordAuthenticationToken(signedUserDetails, "", null);
    }
}
