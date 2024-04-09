package com.genildo.minhasfinancas.service;

import com.genildo.minhasfinancas.model.entity.Usuario;

public interface UsuarioService {
    Usuario autenticar (String email, String senha);
    Usuario salvarUsuario(Usuario usuario);

    void validarEmail(String email);

}
