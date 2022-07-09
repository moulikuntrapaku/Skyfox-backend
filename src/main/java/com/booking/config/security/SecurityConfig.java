package com.booking.config.security;

import com.booking.users.UserPrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserPrincipalService userPrincipalService;

    @Autowired
    public SecurityConfig(UserPrincipalService userPrincipalService) {
        this.userPrincipalService = userPrincipalService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.cors();
       http.csrf().disable();

                http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/version").permitAll()
                .antMatchers(HttpMethod.POST, "/**").permitAll()
                        .antMatchers(HttpMethod.GET,"/user").permitAll()
                        .antMatchers(HttpMethod.GET,"/revenue").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic()

                ;
    }



    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        //daoAuthenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userPrincipalService);
        return daoAuthenticationProvider;
    }



}
