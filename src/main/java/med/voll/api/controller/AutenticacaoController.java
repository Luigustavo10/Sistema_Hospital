package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.usuarios.DadosAutenticacao;
import med.voll.api.domain.usuarios.Usuarios;
import med.voll.api.infra.security.DadosTokenJWT;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {


    @Autowired
    private AuthenticationManager manager;
    // Recebe os dados de autenticação (login e senha) no corpo da requisição,
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(token);

        var tokenJWT = tokenService.gerarToken((Usuarios) authentication.getPrincipal());


        // Se a autenticação for bem-sucedida, gera um token JWT para o usuário autenticado
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
