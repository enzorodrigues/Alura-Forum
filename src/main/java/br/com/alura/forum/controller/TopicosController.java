package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.DetalhesTopicoDTO;
import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.controller.form.PutTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RequestMapping("/topicos")
@RestController
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired private CursoRepository cursoRepository;

    @GetMapping("/listar")
    @Cacheable(value = "listaDeTopicos")
    public Page<TopicoDTO> listar(@RequestParam(required = false) String nomeCurso,
                                  @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page){
        Page<Topico> topicos;
        if(nomeCurso == null) {
            topicos = topicoRepository.findAll(page);
        }
        else {
            topicos = topicoRepository.findByCursoNome(nomeCurso, page);
        }
        return TopicoDTO.convert(topicos);
    }

    @PostMapping("/cadastrar")
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder){
        Topico topico = form.converter(cursoRepository);
        topicoRepository.save(topico);
        URI uri = uriBuilder.path("/topicos/listar/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDTO(topico));
    }

    @GetMapping("listar/{id}")
    public ResponseEntity<DetalhesTopicoDTO> detalhar(@PathVariable Long id){
        Optional<Topico> topico = topicoRepository.findById(id);
        if(topico.isPresent()) {
            return ResponseEntity.ok(new DetalhesTopicoDTO(topico.get()));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/atualizar/{id}")
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody @Valid PutTopicoForm putTopicoForm){
        try{
            Topico topico = putTopicoForm.atualizar(id, topicoRepository);
            return  ResponseEntity.ok(new TopicoDTO(topico));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("{\"Error\": \"Erro ao atualizar topico || Topico não existe\"}");
        }
    }

    @DeleteMapping("/deletar/{id}")
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<?> deletar(@PathVariable Long id){
        try{
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.badRequest().body("{\"Error\": \"Erro ao deletar topico || Topico não existe\"}");
        }
    }
}
