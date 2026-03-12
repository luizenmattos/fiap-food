package com.luizen.autenticacao.infra.entrada;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.luizen.autenticacao.aplicacao.entrada.AutenticarUsuarioInput;
import com.luizen.autenticacao.aplicacao.entrada.RegistrarUsuarioInput;
import com.luizen.autenticacao.aplicacao.entrada.UsuarioApplicationService;
import com.luizen.autenticacao.dominio.Usuario;

@RestController
public class UsuarioController {

    private final TokenService tokenService;
    private final UsuarioApplicationService usuarioApplicationCommandService;
    
    public UsuarioController(TokenService tokenService, UsuarioApplicationService usuarioApplicationCommandService) {
        this.tokenService = tokenService;
        this.usuarioApplicationCommandService = usuarioApplicationCommandService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<ApiResponse> registrarUsuario(@RequestBody RegistrarRequest request) {
        Usuario usuario = usuarioApplicationCommandService.registrarUsuario(new RegistrarUsuarioInput(request.nome(), request.email(), request.senha(), request.papel()));
        return ResponseEntity.ok(new ApiResponse(
            "success", 
            "Usuário registrado com sucesso", 
            new RegistrarResponse(new UsuarioDto(usuario.getNome(), usuario.getEmail(), usuario.getPapel().name())), 
            null));
    }

    @PostMapping("/autenticar")
    public ResponseEntity<ApiResponse> autenticarUsuario(@RequestBody AutenticarRequest request) {
        Usuario usuario = usuarioApplicationCommandService.autenticarUsuario(new AutenticarUsuarioInput(request.email(), request.senha()));
        String tokenJwt = tokenService.gerarToken(usuario);
        
        return ResponseEntity.ok(new ApiResponse(
            "success", 
            "Autenticação realizada com sucesso", 
            new AutenticarResponse(tokenJwt), 
            null));
    }
}

record AutenticarRequest(String email, String senha) {}
record AutenticarResponse(String token) {}

record RegistrarRequest(String nome, String email, String senha, String papel) {}
record RegistrarResponse(UsuarioDto usuario) {}
record UsuarioDto(String nome, String email, String papel) {

}
