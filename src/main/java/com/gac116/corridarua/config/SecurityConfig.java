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

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Usamos a sintaxe de expressão lambda (Spring Security 6+) para a configuração.
        http
            .authorizeHttpRequests(auth -> auth
                // Acesso público usando a sintaxe de string (não precisa de AntPathRequestMatcher aqui)
                .requestMatchers("/", "/login", "/registro").permitAll()
                
                // Permite acesso a recursos estáticos (CSS, JS, etc.)
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                
                // Todas as outras requisições devem ser autenticadas
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true) // Redireciona para a home após o login
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
    
    // Você precisará de um PasswordEncoder e um UserDetailsService aqui também
    // para que a segurança funcione completamente, mas para o erro de compilação, 
    // o foco é no filterChain.
}