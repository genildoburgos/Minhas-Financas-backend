package com.genildo.minhasfinancas.service.impl;

import com.genildo.minhasfinancas.exception.ErroAutenticaçao;
import com.genildo.minhasfinancas.exception.RegraNegocioException;
import com.genildo.minhasfinancas.model.entity.Usuario;
import com.genildo.minhasfinancas.model.repository.UsuarioRepository;
import com.genildo.minhasfinancas.service.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository repository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuario = repository.findByEmail(email);
        if (!usuario.isPresent()) {
            throw new ErroAutenticaçao("Usuario não encontrado para o email encontrado");
        }

        if(!usuario.get().getSenha().equals(senha)) {
            throw new ErroAutenticaçao("Senha invalida");
        }

        return usuario.get();
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());
        return repository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {
        boolean existe = repository.existsByEmail(email);
        if (existe){
            throw new RegraNegocioException("Já existe usuário cadastrado com esse email.");
        }
    }
}
