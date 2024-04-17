package com.genildo.minhasfinancas.service;

import com.genildo.minhasfinancas.exception.RegraNegocioException;
import com.genildo.minhasfinancas.model.entity.Usuario;
import com.genildo.minhasfinancas.model.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.genildo.minhasfinancas.service.UsuarioService;

@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {
    @Autowired
    UsuarioService service;

    @Autowired
    UsuarioRepository repository;

    @Test
    public void deveValidarEmail() {
        // cenario
            repository.deleteAll();


        //acao
            service.validarEmail("email@email.com");

        //
    }

    @Test
    public void deveLancarErrorAoValidarEmailQuandoExistirEmailCadastrado(){
        //cenario
        Usuario usuario = Usuario.builder().nome("usuario").email("email@email.com").build();
        repository.save(usuario);

        //acao
        service.validarEmail("email@email.com");

    }
}
