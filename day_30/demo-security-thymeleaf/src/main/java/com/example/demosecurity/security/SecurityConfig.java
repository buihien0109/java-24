package com.example.demosecurity.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true
)
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Cấu hình đường dẫn
        String[] publicRoutes = new String[]{"/css/**", "/js/**", "/images/**"};
        http.authorizeHttpRequests(auth -> {
//            auth.requestMatchers(publicRoutes).permitAll();
//            auth.requestMatchers("/").permitAll();
//            auth.requestMatchers("/user").hasRole("USER");
//            auth.requestMatchers("/admin").hasRole("ADMIN");
//            auth.requestMatchers("/aa/**", "/bb/**").hasAnyRole("USER", "ADMIN");
//            auth.requestMatchers(HttpMethod.POST, "/cc/**").hasRole("AUTHOR");
//            auth.requestMatchers(HttpMethod.GET, "/dd/**").hasAuthority("ROLE_USER");
//            auth.requestMatchers("/abc/**", "/xyz/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN");
//            auth.anyRequest().authenticated();
            auth.anyRequest().permitAll();
        });

        // Cấu hình form login
        http.formLogin(form -> {
            form.loginPage("/login");
            form.loginProcessingUrl("/login-process");
            form.usernameParameter("email");
            form.passwordParameter("pass");
            form.defaultSuccessUrl("/", true);
            form.permitAll();
        });

        // Cấu hình logout
        http.logout(logout -> {
            logout.logoutSuccessUrl("/");
            logout.invalidateHttpSession(true);
            logout.deleteCookies("JSESSIONID");
            logout.clearAuthentication(true);
            logout.permitAll();
        });

        // Cấu hình xác thục
        http.authenticationProvider(authenticationProvider());

        return http.build();
    }
}
