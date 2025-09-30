package com.asking.api_produit.config.security;

import com.asking.api_produit.config.components.CustomAccessDeniedHandler;
import com.asking.api_produit.config.components.CustomAuthenticationEntryPoint;
import com.asking.api_produit.repository.security.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AccountRepository accountRepository;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorizeRequests ->
                                authorizeRequests
                                        .requestMatchers(
                                                "/swagger-ui/**",
                                                "/v3/api-docs/**",
                                                "/swagger-resources/**",
                                                "/webjars/**",
                                                "/public/**",
                                                "/login",
                                                "/register",
                                                "/css/**",
                                                "/js/**",
                                                "/images/**",
                                                "/logo/**",
                                                "/",
                                                "/product-by-category",
                                                "/info-product",
                                                "/shopping-cart"
                                        ).permitAll()
                                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()

                                        // 🔹 Account
                                        .requestMatchers(HttpMethod.PUT,    "/api/v1/gestion-produit/security/account/update-account/**").hasAnyRole("ADMINISTRATEURS", "UTILISATEUR")
                                        .requestMatchers(HttpMethod.PUT,    "/api/v1/gestion-produit/security/account/check-phone-exist").hasAnyRole("ADMINISTRATEURS")
                                        .requestMatchers(HttpMethod.PUT,    "/api/v1/gestion-produit/security/account/check-email-exist").hasAnyRole("ADMINISTRATEURS")
                                        .requestMatchers(HttpMethod.PUT,    "/api/v1/gestion-produit/security/account/add-user-into-group").hasAnyRole("ADMINISTRATEURS")
                                        .requestMatchers(HttpMethod.POST,   "/api/v1/gestion-produit/security/account/search-account").hasAnyRole("ADMINISTRATEURS")
                                        .requestMatchers(HttpMethod.POST,   "/api/v1/gestion-produit/security/account/add-account").permitAll()
                                        .requestMatchers(HttpMethod.GET,    "/api/v1/gestion-produit/security/account/info-account/**").hasAnyRole("ADMINISTRATEURS", "UTILISATEUR")
                                        .requestMatchers(HttpMethod.GET,    "/api/v1/gestion-produit/security/account/all-account").hasAnyRole("ADMINISTRATEURS")

                                        // 🔹 Rights
                                        .requestMatchers(HttpMethod.PUT,    "/api/v1/gestion-produit/security/rights/update-right/**").hasAnyRole("ADMINISTRATEURS")
                                        .requestMatchers(HttpMethod.POST,   "/api/v1/gestion-produit/security/rights/add-right").hasAnyRole("ADMINISTRATEURS")
                                        .requestMatchers(HttpMethod.GET,    "/api/v1/gestion-produit/security/rights/info-right/**").hasAnyRole("ADMINISTRATEURS")
                                        .requestMatchers(HttpMethod.GET,    "/api/v1/gestion-produit/security/rights/all-rights").hasAnyRole("ADMINISTRATEURS")
                                        .requestMatchers(HttpMethod.DELETE, "/api/v1/gestion-produit/security/rights/delete-right").hasAnyRole("ADMINISTRATEURS")

                                        // 🔹 Authentication
                                        .requestMatchers(HttpMethod.PUT,    "/api/v1/gestion-produit/security/authentication/reset-password").hasAnyRole("ADMINISTRATEURS")
                                        .requestMatchers(HttpMethod.PUT,    "/api/v1/gestion-produit/security/authentication/reset-password/validate-open-link").hasAnyRole("ADMINISTRATEURS")
                                        .requestMatchers(HttpMethod.PUT,    "/api/v1/gestion-produit/security/authentication/reset-password/send-form-to-email").hasAnyRole("ADMINISTRATEURS")
                                        .requestMatchers(HttpMethod.PUT,    "/api/v1/gestion-produit/security/authentication/refresh-token").hasAnyRole("ADMINISTRATEURS")
                                        .requestMatchers(HttpMethod.PUT,    "/api/v1/gestion-produit/security/authentication/change-password").hasAnyRole("ADMINISTRATEURS")
                                        .requestMatchers(HttpMethod.PUT,    "/api/v1/gestion-produit/security/authentication/change-password/validate-old-password").hasAnyRole("ADMINISTRATEURS")
                                        .requestMatchers(HttpMethod.POST,   "/api/v1/gestion-produit/security/authentication/logout").hasAnyRole("ADMINISTRATEURS")
                                        .requestMatchers(HttpMethod.POST,   "/api/v1/gestion-produit/security/authentication/login").permitAll()

                                        // 🔹 Groups
                                        .requestMatchers(HttpMethod.PUT,    "/api/v1/gestion-produit/security/profiles/update-group/**").hasAnyRole("ADMINISTRATEURS")
                                        .requestMatchers(HttpMethod.POST,   "/api/v1/gestion-produit/security/profiles/add-group").hasAnyRole("ADMINISTRATEURS")
                                        .requestMatchers(HttpMethod.GET,    "/api/v1/gestion-produit/security/profiles/info-group/**").hasAnyRole("ADMINISTRATEURS")
                                        .requestMatchers(HttpMethod.GET,    "/api/v1/gestion-produit/security/profiles/all-groups").hasAnyRole("ADMINISTRATEURS")
                                        .requestMatchers(HttpMethod.DELETE, "/api/v1/gestion-produit/security/profiles/delete-group").hasAnyRole("ADMINISTRATEURS")
                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling ->
                                exceptionHandling
                                    .accessDeniedHandler(accessDeniedHandler)
                                    .authenticationEntryPoint(authenticationEntryPoint)
                );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
