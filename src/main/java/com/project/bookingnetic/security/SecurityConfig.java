package com.project.bookingnetic.security;

import com.project.bookingnetic.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@Configuration
@EnableWebSecurity //spring boot know that this is where we keep our security
public class SecurityConfig {

    private UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }


    /*Note that the order of the antMatchers() elements is significant; the more specific rules need to come first,
     followed by the more general ones. */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/user/register").anonymous()
                .requestMatchers("/address", "/accommodation", "/image", "reservation",  "/user/adminPage", "/user/adminPage/**", "/user/addNewUser", "/user/delete/**",
                        "/user/edit-user/**", "/user/update-user/**","/user/view/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers( "/user/account").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginProcessingUrl("/")
                .loginPage("/login").permitAll()
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/")
                .and()
                .rememberMe().tokenValiditySeconds(2592000).key("mySecret!").rememberMeParameter("checkRememberMe")
                .and()
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /* moramo imat DaoAuthenticationProvider kako bi moga dobiveni username i password priko requesta provjerit
    koristi password encoder da validira password, ako autentikacija prođe možemo vadit principal
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(this.userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
