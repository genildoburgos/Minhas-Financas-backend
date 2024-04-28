package com.genildo.minhasfinancas.service;

import com.genildo.minhasfinancas.exception.ErroAutenticaçao;
import com.genildo.minhasfinancas.exception.RegraNegocioException;
import com.genildo.minhasfinancas.model.entity.Usuario;
import com.genildo.minhasfinancas.model.repository.UsuarioRepository;
import com.genildo.minhasfinancas.service.impl.UsuarioServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import com.genildo.minhasfinancas.service.UsuarioService;
import org.springframework.util.Assert;

import javax.swing.text.html.Option;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {
    @SpyBean
    UsuarioService service;

    @MockBean
    UsuarioRepository repository;

    @Test
    public void deveSalvarUmUsuario() {
        // Cenário
        String nome = "nome";
        String email = "email@email.com";
        String senha = "senha";

        Usuario usuarioParaSalvar = Usuario.builder()
                .nome(nome)
                .email(email)
                .senha(senha)
                .build();

        Usuario usuarioSalvo = Usuario.builder()
                .id(1L) // Supondo que o serviço atribuirá um ID
                .nome(nome)
                .email(email)
                .senha(senha)
                .build();

        Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
        Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuarioSalvo);

        // Ação
        Usuario usuarioRetornado = service.salvarUsuario(usuarioParaSalvar);

        // Verificação
        Assertions.assertNotNull(usuarioRetornado);
        Assertions.assertEquals(usuarioSalvo.getId(), usuarioRetornado.getId());
        Assertions.assertEquals(usuarioSalvo.getNome(), usuarioRetornado.getNome());
        Assertions.assertEquals(usuarioSalvo.getEmail(), usuarioRetornado.getEmail());
    }

    @Test
    public void naoDeveSalvarUmUsuarioComOEmailJaCadastrado(){
        // Cenário
        String email = "email@email.com";
        Usuario usuario = Usuario.builder().email(email).build();
        Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(email);

        // Ação
        try {
            service.salvarUsuario(usuario);
        } catch (RegraNegocioException e) {
            // Exceção esperada, não faz nada
        }

        // Verificação
        Mockito.verify(repository, Mockito.never()).save(Mockito.any(Usuario.class));
    }





    @Test
    public void deveAutenticarUmUsuarioComSucesso(){
        //cenario
        String email = "email@email.com";
        String senha = "senha";
        Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
        Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));

        // acao
        Usuario result = service.autenticar(email, senha);

        // verificacao
        Assertions.assertNotNull(result);
    }


    @Test
    public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComOEmailInformado(){
        // cenario
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        //acao

        service.autenticar("email@email.com", "senha");


    }

    @Test
    public void deveLancarErrorQuandoSenhaNaoBater(){
        //cenario
        String senha = "senha";
        Usuario usuario = Usuario.builder().email("email@email.com").senha(senha).build();

        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));


        //acao

        service.autenticar("email@email.com", "senha123");


    }


    @Test
    public void deveValidarEmail() {
        // cenario
            Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);


        //acao
            service.validarEmail("email@email.com");

        //
    }

    @Test
    public void deveLancarErrorAoValidarEmailQuandoExistirEmailCadastrado(){
        //cenario
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

        //acao
        service.validarEmail("email@email.com");

    }
}
