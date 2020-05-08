package com.techcia.services;

import com.techcia.models.Client;
import com.techcia.repositories.ClientRepository;
import com.techcia.security.UserSS;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

@RequiredArgsConstructor
@Configuration
@Primary
public class ClientDetailsServiceImp implements UserDetailsService {

    private final ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<Client> stock = clientRepository.findByEmail(username);
        UserBuilder builder = null;
        if (!stock.isPresent()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        Client client = stock.get();
        List<String> roles = new ArrayList<String>();
        roles.add("CLIENT");
        return new UserSS(client.getId(), client.getEmail(), client.getPassword(), getAuthority(roles));
    }


    private Set<SimpleGrantedAuthority> getAuthority(List<String> roles) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        });
        return authorities;
    }

}
