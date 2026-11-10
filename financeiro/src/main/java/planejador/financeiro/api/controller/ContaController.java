package planejador.financeiro.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import planejador.financeiro.api.dto.ContaRequestDTO;
import planejador.financeiro.api.dto.ContaResponseDTO;
import planejador.financeiro.security.UsuarioDetailsImpl;
import planejador.financeiro.service.ContaService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/contas")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Contas", description = "Endpoints para gerencimento das contas bancárias dos usuários")
public class ContaController {
    private final ContaService contaService;

    @PostMapping
    @Operation(summary = "Criar nova conta", description = "Cadastra nova conta bancária vinculada a um usuário existente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Conta criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos (erro de validação dos campos)"),
            @ApiResponse(responseCode = "404", description = "Usuário informado não foi encontrado")
    })
    public ResponseEntity<ContaResponseDTO> criar(
            @RequestBody @Valid ContaRequestDTO dto,
            @AuthenticationPrincipal UsuarioDetailsImpl usuarioLogado) {
        UUID usuarioId = usuarioLogado.getUsuario().getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(contaService.criarConta(dto, usuarioId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar conta por ID", description = "Recupera os detalhes de uma conta específica pelo seu UUID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Conta encontrada"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    public ResponseEntity<ContaResponseDTO> buscar(@PathVariable UUID id,
                                                   @AuthenticationPrincipal UsuarioDetailsImpl usuarioLogado) {
        return ResponseEntity.ok(contaService.buscarPorId(id, usuarioLogado.getUsuario().getId()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar conta", description = "Atualiza os dados de uma conta existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Conta atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    public ResponseEntity<ContaResponseDTO> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody ContaRequestDTO dto,
            @AuthenticationPrincipal UsuarioDetailsImpl usuarioLogado
    ) {
        return ResponseEntity.ok(contaService.atualizarConta(dto, id,  usuarioLogado.getUsuario().getId()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar conta", description = "Remove uma conta existente do sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Conta removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    public ResponseEntity<Void> deletar(@PathVariable UUID id, @AuthenticationPrincipal UsuarioDetailsImpl usuarioLogado) {
        contaService.deletarConta(id,  usuarioLogado.getUsuario().getId());
        return ResponseEntity.noContent().build();
    }

}
