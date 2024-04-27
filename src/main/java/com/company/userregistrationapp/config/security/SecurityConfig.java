package com.company.userregistrationapp.config.security;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    AuthRequestFilter authRequestFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests().antMatchers("/user/sign-in")
//                .authenticated()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(STATELESS);
//
//        http.addFilterBefore(authRequestFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .authorizeRequests()
                .antMatchers("/user/**").permitAll() // Permit access to /public/** without authentication
                .anyRequest().authenticated() // Require authentication for all other requests
                .and()
                .formLogin(); // Use default form-based login

        // Disable CSRF protection for simplicity (not recommended in production without proper CSRF protection)
        http.csrf().disable();
    }

    //
//    UserRepository userRepository;
//    AuthRequestFilter authRequestFilter;
//
//    DataSource dataSource;
//
//    @Bean
//    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
//
//        //configurations
//        http.csrf(AbstractHttpConfigurer::disable);
//        http.cors(AbstractHttpConfigurer::disable);
//        http.sessionManagement(session -> session.sessionCreationPolicy(STATELESS));
//
////        authorizes
//        http.authorizeHttpRequests(auth -> auth.antMatchers("user/sign-in").permitAll());
//        http.authorizeHttpRequests(auth -> auth.antMatchers("user/sign-up").permitAll());
//        http.authorizeHttpRequests(auth -> auth.antMatchers("user/confirm-email").permitAll());
//        http.authorizeHttpRequests(auth -> auth.antMatchers("user/forgot-password").permitAll());
//        http.authorizeHttpRequests(auth -> auth.antMatchers("user/change-password").permitAll());
//
//        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
//        http.rememberMe(rememberMe -> rememberMe.tokenRepository(persistentTokenRepository()));
//
//        http.apply(new AuthFilterConfigurerAdapter(authRequestFilter));
//        return http.build();
//
//    }
//

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
}
}
