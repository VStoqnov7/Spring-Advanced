package com.example.mobilele.config;

import com.example.mobilele.models.enums.Role;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    private final String rememberMeKey;

    private final UserDetailsService userDetailsService;

    public SecurityConfiguration(@Value("${mobilelele.remember.me.key}")
                                 String rememberMeKey, UserDetailsService userDetailsService) {
        this.rememberMeKey = rememberMeKey;
        this.userDetailsService = userDetailsService;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(
                // Define which urls are visible by which users
                authorizeRequests -> authorizeRequests
                        // All static resources which are situated in js, images, css are available for anyone
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        // Allow anyone to see the home page, the registration page and the login form
                        .antMatchers("/", "/user/login", "/user/register", "/user/login-error","/error").permitAll()
                        .antMatchers("/user/offers//details/update/{offerId}","/user/offers//details/delete/{offerId}",
                                "/user/offers//details/{offerId}").hasRole(Role.ADMIN.name())
                        // all other requests are authenticated.
                        .anyRequest()
                        .authenticated()
        ).formLogin(
                formLogin -> {
                    formLogin
                            // redirect here when we access something which is not allowed.
                            // also this is the page where we perform login.
                            .loginPage("/user/login")
                            // The names of the input fields (in our case in auth-login.html)
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/", true)
                            .failureForwardUrl("/users/login-error");
                }
        ).logout(
                logout -> {
                     logout
                            // the URL where we should POST something in order to perform the logout
                            .logoutUrl("/users/logout")
                            // where to go when logged out?
                            .logoutSuccessUrl("/")
                            // invalidate the HTTP session
                            .invalidateHttpSession(true)
                            .deleteCookies("JSESSIONID");
                }
        ).rememberMe(
                rememberMe -> {
                    rememberMe
                            .key(rememberMeKey)
                            .rememberMeParameter("remember")
                            .rememberMeCookieName("remember")
                            .userDetailsService(userDetailsService);
                }
        ).build();
    }

}
