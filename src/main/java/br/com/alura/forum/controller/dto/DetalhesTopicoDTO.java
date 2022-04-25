package br.com.alura.forum.controller.dto;

import br.com.alura.forum.modelo.StatusTopico;
import br.com.alura.forum.modelo.Topico;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DetalhesTopicoDTO {

    private Long id;
    private  String mensagem;
    private String titulo;
    private LocalDateTime dataCriacao;
    private String nomeAutor;
    private StatusTopico status;
    private List<RespostaDTO> respostas = new ArrayList<>();

    public DetalhesTopicoDTO(Topico topico){
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.dataCriacao = topico.getDataCriacao();
        this.nomeAutor = topico.getAutor().getNome();
        this.status = topico.getStatus();
        this.respostas.addAll(topico.getRespostas().stream().map(RespostaDTO::new).collect(Collectors.toList()));
    }
}
