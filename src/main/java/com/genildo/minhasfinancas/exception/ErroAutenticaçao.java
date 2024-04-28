package com.genildo.minhasfinancas.exception;

public class ErroAutenticaçao extends RuntimeException{
    public ErroAutenticaçao(String mensagem) {
        super(mensagem);
    }
}
