package com.techcia.security;

import com.techcia.services.ClientDetailsServiceImp;
import com.techcia.services.CompanyDetailsServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {



    @Configuration
    @RequiredArgsConstructor
    @Order(1)
    public static class ClientConfigurationAdapter extends WebSecurityConfigurerAdapter{

        private final ClientDetailsServiceImp clientDetailsServiceImp;

        @Bean
        public UserDetailsService clientDetailsService() {
            return clientDetailsServiceImp;
        };

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        };


        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(clientDetailsService()).passwordEncoder(passwordEncoder());
        }

        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception {

            httpSecurity.csrf().disable().antMatcher("/clients/**").authorizeRequests()
                    .antMatchers("/home").permitAll()
                    .antMatchers(HttpMethod.POST, "/clients/login").permitAll()
                    .antMatchers(HttpMethod.POST, "/clients").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    // filtra requisições de login
                    .addFilterBefore(new JWTLoginFilter("/clients/login", authenticationManager()),
                            UsernamePasswordAuthenticationFilter.class)

                    // filtra outras requisições para verificar a presença do JWT no header
                    .addFilterBefore(new JWTAuthenticationFilter(),
                            UsernamePasswordAuthenticationFilter.class);
        }
    }

    @Configuration
    @RequiredArgsConstructor
    @Order(2)
    public static class CompanyConfigurationAdapter extends WebSecurityConfigurerAdapter{

        private final CompanyDetailsServiceImp companyDetailsServiceImp;

        @Bean
        public UserDetailsService companyDetailsService() {
            return companyDetailsServiceImp;
        };

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        };

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(companyDetailsService()).passwordEncoder(passwordEncoder());
        }

        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception {

            httpSecurity.csrf().disable().antMatcher("/companies/**").authorizeRequests()
                    .antMatchers("/home").permitAll()
                    .antMatchers(HttpMethod.POST, "/companies/login").permitAll()
                    .antMatchers(HttpMethod.POST, "/companies").permitAll()
                    .anyRequest().authenticated()
                    .and()

                    // filtra requisições de login
                    .addFilterBefore(new JWTLoginFilter("/companies/login", authenticationManager()),
                            UsernamePasswordAuthenticationFilter.class)

                    // filtra outras requisições para verificar a presença do JWT no header
                    .addFilterBefore(new JWTAuthenticationFilter(),
                            UsernamePasswordAuthenticationFilter.class);

        }
    }


}
