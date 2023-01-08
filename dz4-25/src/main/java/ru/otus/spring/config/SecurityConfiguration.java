package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .antMatchers("/", "/css/**", "/images/**").permitAll()
                // Книги
                .antMatchers("/book/delete/*").hasRole("ADMIN")
                .antMatchers("/book/create", "/book/edit/*").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/book/all", "/book/comments/*").hasAnyRole("ADMIN", "MANAGER", "USER")
                // Авторы
                .antMatchers("/author/delete/*", "/author/create").hasRole("ADMIN")
                .antMatchers("/author/all").hasAnyRole("ADMIN", "MANAGER", "USER")
                .antMatchers("/author/edit/*").hasAnyRole("ADMIN", "MANAGER")
                // Жанры
                .antMatchers("/genre/delete/*", "/genre/create").hasRole("ADMIN")
                .antMatchers("/genre/all").hasAnyRole("ADMIN", "MANAGER", "USER")
                .antMatchers("/genre/edit/*").hasAnyRole("ADMIN", "MANAGER")
                // Комментарии
                .antMatchers("/comment/create/*").hasAnyRole("ADMIN", "USER", "MANAGER")
                .antMatchers("/comment/**").hasAnyRole("ADMIN", "MANAGER")
                .anyRequest().authenticated()
                .and()
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/")
                        .permitAll())
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("userName")
                        .passwordParameter("password")
                        .permitAll()
                );
        return http.build();
    }
}
