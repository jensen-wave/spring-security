package com.eazybytes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;


import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
/*
        http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());
*/
/*
        http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());
*/

        http.authorizeHttpRequests((requests) -> requests.
                        requestMatchers("/myAccount","/myBalance","/myLoans","/myCards").authenticated().
                        requestMatchers("/notices","/contact","/error").permitAll());

        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user")
                .password("{bcrypt}$2a$12$xIOX3LereF.s2CeM5.C5UO21YzZSw9vC5W7MwN5zW5lzZicOzrBdm") // 注意 {noop} 前綴
                .authorities("read")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password("{noop}EazyBytes@54321") // 注意 {noop} 前綴
                .authorities("admin")
                .build();

        // 將所有使用者傳入建構子
        return new InMemoryUserDetailsManager(user, admin);
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

}
