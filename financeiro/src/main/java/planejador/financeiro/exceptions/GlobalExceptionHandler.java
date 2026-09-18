package planejador.financeiro.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // erros validação do @valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidacao(MethodArgumentNotValidException ex){
        Map<String, String> camposComErro = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> camposComErro.put(error.getField(), error.getDefaultMessage()));
        Map<String, Object> corpo = corpoBaseEx(HttpStatus.BAD_REQUEST, "Erro de validação");
        corpo.put("campos", camposComErro);
        return ResponseEntity.badRequest().body(corpo);
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleNaoEncontrado(RecursoNaoEncontradoException ex){
        Map<String, Object> corpo = corpoBaseEx(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.badRequest().body(corpo);
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<Map<String, Object>> handleSaldoInsuficiente(SaldoInsuficienteException ex){
        Map<String, Object> corpo = corpoBaseEx(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.badRequest().body(corpo);
    }

    // erros 400
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleArgumentoInvalido(IllegalArgumentException ex){
        Map<String, Object> corpo = corpoBaseEx(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.badRequest().body(corpo);
    }

    // Qualquer erro que a gente não viu -> 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenerico(Exception ex){
        Map<String, Object> corpo = corpoBaseEx(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno inesperdo");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(corpo);
    }

    @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAutenticacao(org.springframework.security.core.AuthenticationException ex) {
        Map<String, Object> corpo = corpoBaseEx(HttpStatus.UNAUTHORIZED, "E-mail ou senha inválidos");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(corpo);
    }

    private Map<String, Object> corpoBaseEx(HttpStatus status, String mensagem){
        Map<String, Object> corpo = new HashMap<>();
        corpo.put("timestamp", LocalDateTime.now());
        corpo.put("status", status.value());
        corpo.put("error", mensagem);
        return corpo;
    }
}