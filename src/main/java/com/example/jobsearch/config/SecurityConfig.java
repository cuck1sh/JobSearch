package com.example.jobsearch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final DataSource dataSource;
    private static final String USER_QUERY = "select email, password, enabled from USERS where email = ?;";
    private static final String AUTHORITIES_QUERY = "select email, account_type from USERS where email = ?;";

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(USER_QUERY)
                .authoritiesByUsernameQuery(AUTHORITIES_QUERY);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/api/auth/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/auth/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/auth/register").permitAll()
                                .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                                .requestMatchers("/vacancies/*").permitAll()
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
