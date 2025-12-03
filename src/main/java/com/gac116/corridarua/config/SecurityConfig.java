package com.gac116.corridarua.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher; // Importação correta e necessária para o logout

// Removendo a importação de MvcRequestMatcher, que raramente é necessária e causava erro.

@Configuration
@EnableWebSecurity
public class SecurityConfig {

   // ... imports

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(auth -> auth
            // Adicione "/error" aqui para que erros não redirecionem para login
            .requestMatchers("/", "/login", "/registro", "/error").permitAll()
            
            // Seus estáticos
            .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
            
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage("/login")
            .defaultSuccessUrl("/", true)
            .permitAll()
        )
            .logout(logout -> logout
                // Aqui usamos o AntPathRequestMatcher para definir explicitamente o URL de logout.
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) 
                .logoutSuccessUrl("/login?logout") 
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll()
            )
            // Desabilita o CSRF temporariamente; remova esta linha se for ativá-lo
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public org.springframework.security.crypto.password.PasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }
    
}