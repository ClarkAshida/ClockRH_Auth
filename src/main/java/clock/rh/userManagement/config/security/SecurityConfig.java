package clock.rh.userManagement.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF (útil apenas para APIs REST)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Permite acesso a todas as rotas
                )
                .formLogin(form -> form.disable())  // Desabilita o formulário de login
                .httpBasic(httpBasic -> httpBasic.disable()); // Desabilita autenticação básica
        return http.build();
    }
}
