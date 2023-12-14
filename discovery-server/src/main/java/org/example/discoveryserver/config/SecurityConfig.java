package org.example.discoveryserver.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    @Value("eureka.username")
    private String username;
    @Value("eureka.password")
    private String password;
    @Bean
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.inMemoryAuthentication()
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser(username)
                .password(password)
                .authorities("USER");
    }
    @Bean
    public void configure(HttpSecurity httpSecurity)throws Exception{
        httpSecurity.csrf(csrfSpec -> csrfSpec.disable())
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic(Customizer.withDefaults());

    }
}
