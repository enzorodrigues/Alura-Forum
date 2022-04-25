package br.com.alura.forum.repository;

import br.com.alura.forum.modelo.Curso;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CursoRepositoryTest {

    @Autowired
    private CursoRepository cursoRepository;

    @Test
    public void deveriaEncontrarCursoPeloNome(){
        String nomeCurso = "HTML 5";
        Curso curso = cursoRepository.findByNome(nomeCurso);
        assertNotNull(curso);
        assertEquals(nomeCurso, curso.getNome());
    }

    //Curso não existe
    @Test
    public void naoDeveriaEncontrarCursoPeloNome(){
        String nomeCurso = "JPA";
        Curso curso = cursoRepository.findByNome(nomeCurso);
        assertNull(curso);
    }
}
