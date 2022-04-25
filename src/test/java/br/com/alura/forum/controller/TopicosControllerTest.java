package br.com.alura.forum.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TopicosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String tokenAluno = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBUEk6IEbDs3J1bSBkYSBBbHVyYSIsInN1YiI6IjEiLCJpYXQiOjE2NDg4MzQ4MDAsImV4cCI6MTY0ODkyMTIwMH0.QdZSvZTFQGjk4liZMuWXQUfiR-3XC8L4Wt4KN4IADdo";
    private final String tokenModerador = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBUEk6IEbDs3J1bSBkYSBBbHVyYSIsInN1YiI6IjIiLCJpYXQiOjE2NDg4MzQ4MzEsImV4cCI6MTY0ODkyMTIzMX0.LlpgYs5l9pefy0ppvXQG6NXzpYQxFb15LlA2GCpPYoY";

    //Cadastrar - POST
    @Test
    public void POST_cadastrar_deveDevolver400_BodyIncorreto() throws Exception {
        URI uri = new URI("/topicos/cadastrar");
        String json = "{\"titulo\": \"\"," +
                "\"mensagem\": \"Mensagem\"," +
                "\"nomeCurso\": \"CSS3\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri).content(json).header("Authorization", tokenAluno).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void POST_cadastrar_deveDevolver403_SemAutorizacao() throws Exception {
        URI uri = new URI("/topicos/cadastrar");
        String json = "{\"titulo\": \"BlaBlaBla\"," +
                "\"mensagem\": \"Mensagem\"," +
                "\"nomeCurso\": \"CSS3\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    public void POST_cadastrar_deveDevolver201_BodyCorretoComHeader() throws Exception {
        URI uri = new URI("/topicos/cadastrar");
        String json = "{\"titulo\": \"Duvida Teste\"," +
                "\"mensagem\": \"Mensagem\"," +
                "\"nomeCurso\": \"CSS3\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri).content(json).header("Authorization", tokenAluno).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(201));
    }


    //Deletar - DELETE
    @Test
    public void DELETE_deletar_deveDevolver403_SemHeaderDeAutorizacao() throws Exception {
        Long id = 1l;
        URI uri = new URI("/topicos/deletar/"+id);

        mockMvc.perform(MockMvcRequestBuilders.delete(uri).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    public void DELETE_deletar_deveDevolver403_RoleAluno() throws Exception {
        Long id = 1l;
        URI uri = new URI("/topicos/deletar/"+id);

        mockMvc.perform(MockMvcRequestBuilders.delete(uri).header("Authorization", tokenAluno).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    public void DELETE_deletar_deveDevolver400_RoleModerador_ErrorOuTopicoNaoExiste() throws Exception {
        Long id = 6899l;
        URI uri = new URI("/topicos/deletar/"+id);

        mockMvc.perform(MockMvcRequestBuilders.delete(uri).header("Authorization", tokenModerador).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void DELETE_deletar_deveDevolver200_RoleModerador_TopicoExistente() throws Exception {
        Long id = 1l;
        URI uri = new URI("/topicos/deletar/"+id);

        mockMvc.perform(MockMvcRequestBuilders.delete(uri).header("Authorization", tokenModerador).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }


    //atualizar - PUT
    @Test
    public void PUT_atualizar_deveDevolver400_BodyIncorreto() throws Exception {
        Long id = 3l;
        URI uri = new URI("/topicos/atualizar/"+id);
        String json = "{\"titulo\": \"\"," +
                        "\"mensagem\": \"\"}";

        mockMvc.perform(MockMvcRequestBuilders.put(uri).content(json).header("Authorization", tokenAluno).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void PUT_atualizar_deveDevolver403_SemAutorizacao() throws Exception {
        Long id = 3l;
        URI uri = new URI("/topicos/atualizar/"+id);
        String json = "{\"titulo\": \"Modifica duvida 3\"," +
                "\"mensagem\": \"Testando Modificação\"}";

        mockMvc.perform(MockMvcRequestBuilders.put(uri).content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    public void PUT_atualizar_deveDevolver200_BodyCorretoComHeader() throws Exception {
        Long id = 3l;
        URI uri = new URI("/topicos/atualizar/"+id);
        String json = "{\"titulo\": \"Modifica duvida 3\"," +
                "\"mensagem\": \"Testando Modificação\"}";

        mockMvc.perform(MockMvcRequestBuilders.put(uri).content(json).header("Authorization", tokenAluno).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void PUT_atualizar_deveDevolver400_TopicoNaoExiste() throws Exception {
        Long id = 6458l;
        URI uri = new URI("/topicos/atualizar/"+id);
        String json = "{\"titulo\": \"Modifica duvida 3\"," +
                "\"mensagem\": \"Testando Modificação\"}";

        mockMvc.perform(MockMvcRequestBuilders.put(uri).content(json).header("Authorization", tokenAluno).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }


    //detalhar - GET

    @Test
    public void GET_detalhar_deveDevolver200_TopicoExiste() throws Exception {
        Long id = 3l;
        URI uri = new URI("/topicos/listar/"+id);

        mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void GET_detalhar_deveDevolver400_TopicoNaoExiste() throws Exception {
        Long id = 6458l;
        URI uri = new URI("/topicos/listar/"+id);

        mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }


    //listar - GET

    //nomeCurso nulo
    @Test
    public void GET_listar_deveDevolver200_ListarTodosOsTopicos() throws Exception {
        URI uri = new URI("/topicos/listar");

        mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void GET_listar_deveDevolver200_ListarPorNomeDoCurso() throws Exception {
        String nomeCurso = "Spring+Boot";
        URI uri = new URI("/topicos/listar?nomeCurso="+nomeCurso);

        mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

}
