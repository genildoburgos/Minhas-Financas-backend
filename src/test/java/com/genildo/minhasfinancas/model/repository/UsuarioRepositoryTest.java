package com.genildo.minhasfinancas.model.repository;

import com.genildo.minhasfinancas.model.entity.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioRepositoryTest {
    @Autowired
    UsuarioRepository repository;

    @Test
    public void deveVerificarAExistenciaDeUmEmail(){
        //cenario
        Usuario usuario = Usuario.builder().nome("Usuario").email("usuario@email.com").build();
        repository.save(usuario);

        //ação/execução
        boolean result = repository.existsByEmail("usuario@email.com");

        //verificação
//        Assertions.assertThat(result).isTrue();
         Assertions.assertTrue(result);
    }
}
