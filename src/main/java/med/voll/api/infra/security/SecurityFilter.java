package med.voll.api.infra.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = recuperarToken(request);

        //        Esse bloco verifica se existe um token JWT na requisição e, caso exista, autentica o usuário no contexto de segurança do Spring. Linha a linha:
//        - `if(token != null) {`: verifica se o token foi recuperado do header Authorization.
//            - `var subject = tokenService.getSubject(token);`: extrai o "subject" (identificador do usuário) do token JWT.
//            - `var usuario = repository.findByLogin(subject);`: busca o usuário no banco de dados usando o subject extraído.
//                    - `var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());`: cria um objeto de autenticação do Spring com o usuário e suas permissões.???
        if(token != null) {
            var subject = tokenService.getSubject(token);
            var usuario = repository.findByLogin(subject);

            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }


        filterChain.doFilter(request, response);

    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
       if( authorizationHeader != null) {
           return authorizationHeader.replace("Bearer ", "");
       }
         return null;
    }


}
