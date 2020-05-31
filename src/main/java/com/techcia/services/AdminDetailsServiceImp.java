package com.techcia.services;

import com.techcia.models.Admin;
import com.techcia.models.Client;
import com.techcia.repositories.AdminRepository;
import com.techcia.repositories.ClientRepository;
import com.techcia.security.UserSS;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

@RequiredArgsConstructor
@Configuration
public class AdminDetailsServiceImp implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<Admin> stock = adminRepository.findByUsername(username);
        User.UserBuilder builder = null;
        if (!stock.isPresent()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        Admin admin = stock.get();
        List<String> roles = new ArrayList<String>();
        roles.add("ADMIN");
        return new UserSS(admin.getId(), admin.getUsername(), admin.getPassword(), getAuthority(roles));
    }


    private Set<SimpleGrantedAuthority> getAuthority(List<String> roles) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        });
        return authorities;
    }

}
