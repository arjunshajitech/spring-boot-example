package com.example.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Order(101)
public class S_101_UserSecurityConfig {

    @Bean
    SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(Customizer.withDefaults());
        http.formLogin(Customizer.withDefaults());

        http.securityMatcher("/api/v1/user/**")
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(UNSECURE_URLS).permitAll()
                        .anyRequest().authenticated()
                );

        http.logout((logout) -> {
            logout.logoutUrl("/api/v1/user/logout");
            logout.clearAuthentication(true);
            logout.invalidateHttpSession(true);
            logout.deleteCookies("JSESSIONID", "SESSION");
        });

        return http.build();
    }


    public static final String[] UNSECURE_URLS = {
            "/api/v1/user/register",
            "/api/v1/user/login",
    };

}
