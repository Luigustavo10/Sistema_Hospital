package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private SecurityFilter securityFilter;

    // Define um bean que configura a segurança da aplicação.
    // Esse método desativa a proteção contra CSRF (ataques de falsificação de requisições),
    // pois normalmente APIs não precisam dessa proteção.
    // Também define que a aplicação não vai criar sessões de usuário (stateless),
    // ou seja, cada requisição é independente e não guarda informações do usuário entre elas.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll()// Permite acesso livre ao endpoint de login
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll() // Permite acesso livre aos endpoints de documentação da API
                        .anyRequest().authenticated()// Exige autenticação para qualquer outra requisição

                );

        return http. addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // Adiciona o filtro de segurança personalizado antes do filtro padrão de autenticação do Spring Security
                .build();
    }

    // Define um bean que fornece o AuthenticationManager, que é responsável por gerenciar a autenticação de usuários.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // Define um bean que cria um codificador de senhas usando o algoritmo BCrypt.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
