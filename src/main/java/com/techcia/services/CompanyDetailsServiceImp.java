package com.techcia.services;

import com.techcia.models.Client;
import com.techcia.models.Company;
import com.techcia.repositories.ClientRepository;
import com.techcia.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
@Configuration
public class CompanyDetailsServiceImp implements UserDetailsService {

    private final CompanyRepository companyRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        System.out.println("Empresa");
        Optional<Company> stock = companyRepository.findByEmail(username);
        User.UserBuilder builder = null;
        if (stock.isPresent()) {
            Company company = stock.get();
            builder = org.springframework.security.core.userdetails.User.withUsername(username).roles("COMPANY");;
            builder.password(company.getPassword());
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
        return builder.build();
    }

}
