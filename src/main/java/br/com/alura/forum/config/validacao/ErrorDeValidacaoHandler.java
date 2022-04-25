package br.com.alura.forum.config.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErrorDeValidacaoHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorFormularioDTO> handle(MethodArgumentNotValidException exception){
        List<ErrorFormularioDTO> errorFormularioDTOS = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        for(FieldError fieldError : fieldErrors){
            String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            ErrorFormularioDTO erro = new ErrorFormularioDTO(fieldError.getField(), message);
            errorFormularioDTOS.add(erro);
        }

        return errorFormularioDTOS;
    }
}
