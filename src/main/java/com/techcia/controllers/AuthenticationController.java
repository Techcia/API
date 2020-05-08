package com.techcia.controllers;

import com.techcia.security.AccountCredentials;
import com.techcia.security.AuthToken;
import com.techcia.security.TokenProvider;
import com.techcia.services.ClientDetailsServiceImp;
import com.techcia.services.CompanyDetailsServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {

    private final ClientDetailsServiceImp clientDetailsServiceImp;
    private final CompanyDetailsServiceImp companyDetailsServiceImp;
    private final TokenProvider tokenProvider;
    BCryptPasswordEncoder bcrypt= new BCryptPasswordEncoder();

    private final TokenProvider jwtTokenUtil;

    @RequestMapping(value = "/client", method = RequestMethod.POST)
    public ResponseEntity<?> registerClient(@RequestBody AccountCredentials loginUser) throws AuthenticationException {

        UserDetails user = clientDetailsServiceImp.loadUserByUsername(loginUser.getUsername());

        boolean isPasswordMatches= bcrypt.matches(loginUser.getPassword(), user.getPassword());
        if(!isPasswordMatches){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        final String token = jwtTokenUtil.generateToken(user);
        return ResponseEntity.ok(new AuthToken(token));
    }

    @RequestMapping(value = "/company", method = RequestMethod.POST)
    public ResponseEntity<?> registerCompany(@RequestBody AccountCredentials loginUser) throws AuthenticationException {
        UserDetails user = companyDetailsServiceImp.loadUserByUsername(loginUser.getUsername());

        boolean isPasswordMatches= bcrypt.matches(loginUser.getPassword(), user.getPassword());
        if(!isPasswordMatches){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        final String token = jwtTokenUtil.generateToken(user);
        return ResponseEntity.ok(new AuthToken(token));
    }


}

