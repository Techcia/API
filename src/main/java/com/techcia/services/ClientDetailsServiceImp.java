package com.techcia.services;

import com.techcia.models.Client;
import com.techcia.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@RequiredArgsConstructor
@Configuration
public class ClientDetailsServiceImp implements UserDetailsService {

    private final ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<Client> stock = clientRepository.findByEmail(username);
        UserBuilder builder = null;
        if (stock.isPresent()) {
            Client client = stock.get();
            builder = org.springframework.security.core.userdetails.User.withUsername(username).roles("CLIENT");;
            builder.password(client.getPassword());
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
        return builder.build();
    }

}
