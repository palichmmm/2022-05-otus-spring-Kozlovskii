package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                                .antMatchers(HttpMethod.GET,"/webjars/**", "/img/*").permitAll()
                                // HomeController
                                .antMatchers(HttpMethod.GET,"/", "/login").permitAll()
                                // UploadController
                                .antMatchers(HttpMethod.GET,"/upload/form", "/download/*").permitAll()
                                .antMatchers(HttpMethod.POST, "/upload/form").permitAll()
                                .antMatchers(HttpMethod.DELETE, "/upload/form/*").permitAll()
                                // FileController
//                        .antMatchers("/upload").hasRole("ADMIN")
                                .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("userName")
                        .passwordParameter("password")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );
        return http.build();
    }
}