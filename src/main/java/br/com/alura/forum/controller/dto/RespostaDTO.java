package br.com.alura.forum.controller.dto;

import br.com.alura.forum.modelo.Resposta;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RespostaDTO {

    private long id;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private String nomeAutor;

    public RespostaDTO(Resposta resposta){
        this.id = resposta.getId();
        this.mensagem = resposta.getMensagem();
        this.dataCriacao = resposta.getDataCriacao();
        this.nomeAutor = resposta.getAutor().getNome();
    }
}
