package com.techcia.services;

import com.techcia.models.Client;
import com.techcia.models.Company;
import com.techcia.repositories.CompanyRepository;
import com.techcia.security.UserSS;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

@RequiredArgsConstructor
@Configuration
public class CompanyDetailsServiceImp implements UserDetailsService {

    private final CompanyRepository companyRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<Company> stock = companyRepository.findByEmail(username);
        User.UserBuilder builder = null;
        if (!stock.isPresent()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        Company company = stock.get();
        List<String> roles = new ArrayList<String>();
        roles.add("COMPANY");
        return new UserSS(company.getId(), company.getEmail(), company.getPassword(), getAuthority(roles));
    }


    private Set<SimpleGrantedAuthority> getAuthority(List<String> roles) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        });
        return authorities;
    }

}
