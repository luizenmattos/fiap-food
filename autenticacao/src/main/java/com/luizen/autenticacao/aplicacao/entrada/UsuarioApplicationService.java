package com.luizen.autenticacao.aplicacao.entrada;

import java.util.Map;

import com.luizen.autenticacao.dominio.Usuario;
import com.luizen.autenticacao.dominio.repositories.UsuarioRepository;
import com.luizen.autenticacao.dominio.services.UsuarioService;
import com.luizen.autenticacao.kernelcompartilhado.MyLogger;
import com.luizen.autenticacao.kernelcompartilhado.MyLoggerMessage;

public class UsuarioApplicationService {

    private static final MyLogger logger = MyLogger.getInstance(UsuarioApplicationService.class);
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    public UsuarioApplicationService(UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository; 
    }

    public Usuario registrarUsuario(RegistrarUsuarioInput input){
        try{
            //Validar se já existe um usuário com o mesmo e-mail
            usuarioRepository.buscarPorEmail(input.email()).ifPresent(
                (usuarioExistente) -> { throw new ApplicationException("Já existe um usuário registrado com o e-mail informado."); }
            );
                
            //Criar o usuario e validar as regras de negócio
            Usuario usuario = usuarioService.registrarUsuario(input.nome(), input.email(), input.senha(), input.papel());
            
            //Salvar o usuário no repositório
            Usuario usuarioSalvo = usuarioRepository.salvar(usuario)
                .orElseThrow(() -> new ApplicationException("Falha ao salvar o usuário."));
                
            //Log do evento
            logger.info(MyLoggerMessage.of("SUCESSO", "REGISTRAR_USUARIO", Map.of(
                "input.nome", input.nome(), 
                "input.email", input.email(), 
                "input.papel", input.papel(),
                "usuario.id", usuarioSalvo.getId().toString(),
                "usuario.nome", usuarioSalvo.getNome(),
                "usuario.email", usuarioSalvo.getEmail(),
                "usuario.papel", usuarioSalvo.getPapel().name()
            )));
            return usuarioSalvo;
        }catch(Exception e){
            //Log do evento
            logger.error(MyLoggerMessage.of("ERRO", "REGISTRAR_USUARIO", Map.of(
                "input.nome", input.nome(), 
                "input.email", input.email(), 
                "input.papel", input.papel(),
                "error", e.getMessage()
            )));
            
           throw e;
        }
    }

    public Usuario autenticarUsuario(AutenticarUsuarioInput input){
        try{
            //Validar se o usuário existe
            Usuario usuario = usuarioRepository.buscarPorEmail(input.email())   
                .orElseThrow(() -> new ApplicationException("Usuário não encontrado para o e-mail informado."));
            
            //Validar a senha do usuário
            Usuario usuarioAutenticado = usuarioService.autenticarUsuario(usuario, input.senha());
            
            //Log do evento
            logger.info(MyLoggerMessage.of("SUCESSO", "AUTENTICAR_USUARIO", Map.of(
                "input.email", input.email(), 
                "usuario.id", usuarioAutenticado.getId().toString(),
                "usuario.nome", usuarioAutenticado.getNome(),
                "usuario.email", usuarioAutenticado.getEmail(),
                "usuario.papel", usuarioAutenticado.getPapel().name()
            )));

            return usuarioAutenticado;

        }catch(Exception e){
            //Log do evento
            logger.error(MyLoggerMessage.of("ERRO", "AUTENTICAR_USUARIO", Map.of(
                "input.email", input.email(), 
                "error", e.getMessage()
            )));
           throw e;
        }
    }
}
