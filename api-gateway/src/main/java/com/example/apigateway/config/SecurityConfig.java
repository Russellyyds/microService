package com.example.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.EnableWebFlux;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity){
    serverHttpSecurity.csrf(csrfSpec -> csrfSpec.disable()).authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
            .pathMatchers("/eureka/**")
            .permitAll()
            .anyExchange()
            .authenticated())
            .oauth2ResourceServer((oath2)->oath2.jwt(Customizer.withDefaults()));
     return serverHttpSecurity.build();
    }
}
