package com.genildo.minhasfinancas.service.impl;

import com.genildo.minhasfinancas.exception.RegraNegocioException;
import com.genildo.minhasfinancas.model.entity.Usuario;
import com.genildo.minhasfinancas.model.repository.UsuarioRepository;
import com.genildo.minhasfinancas.service.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return null;
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
