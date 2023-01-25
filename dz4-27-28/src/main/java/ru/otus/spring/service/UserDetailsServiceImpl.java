package ru.otus.spring.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.spring.models.Authority;
import ru.otus.spring.models.User;
import ru.otus.spring.repository.UserRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = repository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException("Unknown user: " + userName));
        if (user == null) {
            throw new UsernameNotFoundException("Unknown user: " + userName);
        }
        Set<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(Authority::getAuthority)
                .map(SimpleGrantedAuthority::new).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                user.getEnabled(),
                user.getAccountNonExpired(),
                user.getAccountNonLocked(),
                user.getCredentialsNonExpired(),
                grantedAuthorities);
    }
}
