package br.com.alura.forum.config.validacao;

import lombok.Getter;

@Getter
public class ErrorFormularioDTO {

    private  String campo;
    private String error;

    public ErrorFormularioDTO(String campo, String error) {
        this.campo = campo;
        this.error = error;
    }
}
