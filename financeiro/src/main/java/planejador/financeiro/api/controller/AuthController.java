package planejador.financeiro.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import planejador.financeiro.api.dto.LoginRequestDTO;
import planejador.financeiro.api.dto.LoginResponseDTO;
import planejador.financeiro.security.JwtService;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Autenticação", description = "Login e geração de token JWT")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Autentica com e-mail e senha, retorna um token JWT.")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha())
        );

        String token = jwtService.gerarToken(dto.getEmail());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
