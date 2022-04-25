package br.com.alura.forum.controller.dto;

import br.com.alura.forum.modelo.Topico;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TopicoDTO {
    private Long id;
    private  String mensagem;
    private String titulo;
    private LocalDateTime dataCriacao;

    public TopicoDTO(Topico topico) {
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.dataCriacao = topico.getDataCriacao();
    }

    public static Page<TopicoDTO> convert(Page<Topico> topicos) {
        return topicos.map(TopicoDTO::new);
    }
}
