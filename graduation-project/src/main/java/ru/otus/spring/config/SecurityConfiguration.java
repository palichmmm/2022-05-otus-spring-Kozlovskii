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
                                .antMatchers(HttpMethod.GET,"/webjars/**", "/img/*", "/js/*").permitAll()
                                // HomeController
                                .antMatchers(HttpMethod.GET,"/", "/login").permitAll()
                                // UploadController
                                .antMatchers(HttpMethod.GET,"/upload/form").permitAll()
                                .antMatchers(HttpMethod.POST,"/upload/form").permitAll()
                                // DownloadController
                                .antMatchers(HttpMethod.GET,"/download/*").permitAll()
                                // NumberController
                                .antMatchers(HttpMethod.GET,"/number/list").permitAll()
                                .antMatchers(HttpMethod.POST,"/number/track").permitAll()
                                // FormatController
                                .antMatchers(HttpMethod.GET,"/format/*").permitAll()
                                .antMatchers(HttpMethod.POST,"/format/form").permitAll()
                                // FileRestController
                                .antMatchers(HttpMethod.GET,"/api/file").permitAll()
                                .antMatchers(HttpMethod.PUT,"/api/file").permitAll()
                                .antMatchers(HttpMethod.PATCH,"/api/file").permitAll()
                                .antMatchers(HttpMethod.DELETE,"/api/file/*").permitAll()
                                .anyRequest().authenticated()
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
