package com.example.jobsearch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
//                        .defaultSuccessUrl("/")
                        .successHandler(successHandler())
                        .failureUrl("/auth/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll())
//                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/static/js/auth/**").permitAll()
                                .requestMatchers("/static/js/search.js").permitAll()
                                .requestMatchers("/api/users/setLng").permitAll()
                                .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                                .requestMatchers("/vacancies/*").permitAll()
                                .requestMatchers("/search").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/vacancies/search").permitAll()
                                .requestMatchers("/employee/**").hasAuthority("EMPLOYEE")
                                .requestMatchers("/employer/**").hasAuthority("EMPLOYER")
                                .requestMatchers(HttpMethod.POST, "/employee/**").hasAuthority("EMPLOYEE")
                                .requestMatchers(HttpMethod.POST, "/employer/**").hasAuthority("EMPLOYER")
                                .requestMatchers("/users/profile").fullyAuthenticated()
                                .anyRequest().fullyAuthenticated()
                );
        return http.build();
    }
}
