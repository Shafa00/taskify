package com.taskify.configration;

import com.taskify.security.AuthenticationFilter;
import com.taskify.security.AuthorizationFilter;
import com.taskify.security.JwtService;
import com.taskify.service.UserService;
import com.taskify.utility.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.taskify.utility.UrlConstant.*;

@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final AuthorizationFilter authorizationFilter;
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable();

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGNUP_URL).permitAll()
                .antMatchers(HttpMethod.POST, CONFIRM_OTP_URL).permitAll()
                .antMatchers(HttpMethod.POST, LOGIN_URL).permitAll()
                .antMatchers(HttpMethod.POST, ADD_USER_URL).hasRole(UserRole.ADMIN.getRoleName())

                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/swagger-ui.html/**").permitAll()
                .antMatchers("/v2/api-docs/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()

                .anyRequest().authenticated();

        http
                .addFilter(new AuthenticationFilter(authenticationManager(), userService, jwtService));

        http
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .headers().frameOptions().disable();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
