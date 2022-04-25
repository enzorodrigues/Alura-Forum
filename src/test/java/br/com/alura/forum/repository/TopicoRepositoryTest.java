package br.com.alura.forum.repository;

import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TopicoRepositoryTest {
    @Autowired
    private TopicoRepository topicoRepository;

    @Test
    public void deveriaEncontrarPeloNomeDoCurso(){
        String nome = "Spring Boot";
        Pageable page = Pageable.unpaged();
        Page<Topico> topicos = topicoRepository.findByCursoNome(nome, page);

        for(Topico topico : topicos){
            assertTrue(topico.getCurso().getNome().equals(nome));
            assertNotNull(topico);
        }
    }

    @Test
    public void naoDeveriaEncontrarPeloNomeDoCurso(){
        String nome = "";
        Pageable page = Pageable.unpaged();
        Page<Topico> topicos = topicoRepository.findByCursoNome(nome, page);

        assertTrue(topicos.getSize() == 0);
    }
}
