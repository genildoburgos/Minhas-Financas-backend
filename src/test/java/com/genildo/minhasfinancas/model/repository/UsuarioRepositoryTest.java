package com.genildo.minhasfinancas.model.repository;

import com.genildo.minhasfinancas.model.entity.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {
    @Autowired
    UsuarioRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void deveVerificarAExistenciaDeUmEmail(){
        //cenario
        Usuario usuario = criarUsuario();
        entityManager.persist(usuario);

        //ação/execução
        boolean result = repository.existsByEmail("usuario@email.com");

        //verificação
//        Assertions.assertThat(result).isTrue();
         Assertions.assertTrue(result);
    }

    @Test
    public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmnail(){
        //cenario

        // açao
        boolean result = repository.existsByEmail("usuario@email.com");

        //verificar
        Assertions.assertFalse(result);
    }

    @Test
    public void devePersistirUmUsuarioNaBaseDeDados(){
        //cenario
        Usuario usuario = criarUsuario();

        // acao
        Usuario usuarioSalvo = repository.save(usuario);

        //verificação
        Assertions.assertNotNull(usuarioSalvo);
    }

    @Test
    public void deveBuscarUmUsuarioPorEmail(){
        //cenario
        Usuario usuario = criarUsuario();
        entityManager.persist(usuario);


        //verificação
        Optional<Usuario> result =  repository.findByEmail("usuario@email.com");
        Assertions.assertTrue(result.isPresent());


    }

    @Test
    public void deveRetornarVazioAoBuscarUmUsuarioPorEmailQuandoNaoExisteNaBaseDeDados(){
        //cenario

        //verificação
        Optional<Usuario> result =  repository.findByEmail("usuario@email.com");
        Assertions.assertFalse(result.isPresent());


    }

    public static Usuario criarUsuario(){
        return Usuario
                .builder()
                .nome("Usuario")
                .email("usuario@email.com")
                .senha("senha")
                .build();
    }

}
