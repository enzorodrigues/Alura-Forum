package br.com.alura.forum.controller.dto;

import lombok.Getter;

@Getter
public class TokenDTO {

    private String token;

    public TokenDTO(String token, String tipo) {
        this.token = tipo+" "+token;
    }
}
