package br.com.alura.forum.controller;

import br.com.alura.forum.config.security.TokenService;
import br.com.alura.forum.controller.dto.TokenDTO;
import br.com.alura.forum.controller.form.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Profile( value = {"prod", "test"})
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDTO> autencicacao(@RequestBody @Valid LoginForm form){
        UsernamePasswordAuthenticationToken dadoLogin = form.converter();
        try {
            Authentication authentication = authManager.authenticate(dadoLogin);
            String token = tokenService.getToken(authentication);
            return  ResponseEntity.ok(new TokenDTO(token, "Bearer"));
        } catch (AuthenticationException ex){
            return ResponseEntity.badRequest().build();
        }

    }
}
