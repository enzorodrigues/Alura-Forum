package br.com.alura.forum.controller.form;

import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.TopicoRepository;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class PutTopicoForm {
    @NotNull
    @NotEmpty
    @Length(min = 5)
    private  String titulo;
    @NotNull @NotEmpty
    private String mensagem;

    public PutTopicoForm(){

    }

    public Topico atualizar(Long id, TopicoRepository topicoRepository) {
        Topico topico = topicoRepository.getById(id);
        topico.setTitulo(this.titulo);
        topico.setMensagem(this.mensagem);
        topicoRepository.save(topico);
        return topico;
    }
}
