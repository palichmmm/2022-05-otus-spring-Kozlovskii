package ru.otus.string.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                        .antMatchers("/", "/css/**", "/images/**", "/webjars/**").permitAll()
                        // Книги
                        .antMatchers("/book/delete/*").hasAuthority("DELETE")
                        .antMatchers("/book/create").hasAuthority("CREATE")
                        .antMatchers("/book/edit/*").hasAuthority("WRITE")
                        .antMatchers("/book/all", "/book/comments/*").hasAuthority("READ")
                        // Авторы
                        .antMatchers("/author/delete/*").hasAuthority("DELETE")
                        .antMatchers("/author/create").hasAuthority("CREATE")
                        .antMatchers("/author/edit/*").hasAuthority("WRITE")
                        .antMatchers("/author/all").hasAuthority("READ")
                        // Жанры
                        .antMatchers("/genre/delete/*").hasAuthority("DELETE")
                        .antMatchers("/genre/create").hasAuthority("CREATE")
                        .antMatchers("/genre/edit/*").hasAuthority("WRITE")
                        .antMatchers("/genre/all").hasAuthority("READ")
                        // Комментарии
                        .antMatchers("/comment/delete/*").hasAuthority("DELETE")
                        .antMatchers("/comment/create/*", "/comment/edit/*").hasAnyAuthority("ADMIN", "USER", "MANAGER")
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
