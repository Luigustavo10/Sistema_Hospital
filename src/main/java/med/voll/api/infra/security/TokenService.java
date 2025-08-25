package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import med.voll.api.domain.usuarios.Usuarios;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    // Método responsável por gerar um token JWT para um usuário
    public String gerarToken(Usuarios usario) {
        try {
            // Cria o algoritmo de assinatura do token usando uma chave secreta
            var algotitimo = Algorithm.HMAC256(secret);
            // Cria o token JWT com informações do usuário, emissor, data de expiração e assina com o algoritmo
            return JWT.create()
                    .withIssuer("API Voll.med") // Quem está emitindo o token
                    .withSubject(usario.getLogin()) // Identificação do usuário
                    .withExpiresAt(dataExpiracao()) // Define quando o token expira
                    .sign(algotitimo); // Assina o token
        } catch (JWTCreationException ex) {
            // Caso ocorra algum erro na criação do token, lança uma exceção
            throw new RuntimeException("Erro ao gerar token JWT", ex);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
        var algoritmo = Algorithm.HMAC256(secret);
        return JWT.require(algoritmo)
                .withIssuer("API Voll.med")
                .build()
                .verify(tokenJWT)
                .getSubject();
        }catch (JWTVerificationException ex) {
            throw new RuntimeException("Token JWT inválido ou expirado!", ex);
        }
    }

    // Método que calcula a data e hora de expiração do token (2 horas a partir de agora)
    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
