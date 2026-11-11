package planejador.financeiro.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import planejador.financeiro.api.dto.TransacaoRequestDTO;
import planejador.financeiro.api.dto.TransacaoResponseDTO;
import planejador.financeiro.security.UsuarioDetailsImpl;
import planejador.financeiro.service.TransacaoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transacoes")
@RequiredArgsConstructor
@Tag(name = "Transações", description = "Endpoints para gerenciamento de transações financeiras")
public class TransacaoController {

    private final TransacaoService transacaoService;

    @GetMapping
    @Operation(
            summary = "Listar transações do usuário autenticado",
            description = "Retorna o extrato completo de transações vinculadas a todas as contas do usuário logado."
    )
    public ResponseEntity<List<TransacaoResponseDTO>> listarDoUsuario(
            @AuthenticationPrincipal UsuarioDetailsImpl usuarioLogado) {
        return ResponseEntity.ok(
                transacaoService.listarDoUsuario(usuarioLogado.getUsuario().getId()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar transação por ID", description = "Recupera os detalhes de uma transação do usuário logado pelo seu UUID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transação encontrada"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada")
    })
    public ResponseEntity<TransacaoResponseDTO> buscarPorId(
            @PathVariable UUID id,
            @AuthenticationPrincipal UsuarioDetailsImpl usuarioLogado) {
        return ResponseEntity.ok(
                transacaoService.buscarPorId(id, usuarioLogado.getUsuario().getId()));
    }

    @GetMapping("/conta/{contaId}")
    @Operation(
            summary = "Listar transações por conta",
            description = "Retorna o histórico de transações vinculadas a uma conta do usuário logado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de transações recuperada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    public ResponseEntity<List<TransacaoResponseDTO>> listarPorConta(
            @PathVariable UUID contaId,
            @AuthenticationPrincipal UsuarioDetailsImpl usuarioLogado) {
        return ResponseEntity.ok(
                transacaoService.listarPorConta(contaId, usuarioLogado.getUsuario().getId()));
    }

    @PostMapping
    @Operation(
            summary = "Criar nova transação",
            description = "Cadastra uma transação financeira vinculada a uma conta e categoria do usuário logado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Transação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos (erro de validação dos campos)"),
            @ApiResponse(responseCode = "404", description = "Conta ou categoria informada não foi encontrada")
    })
    public ResponseEntity<TransacaoResponseDTO> criar(
            @RequestBody @Valid TransacaoRequestDTO dto,
            @AuthenticationPrincipal UsuarioDetailsImpl usuarioLogado) {
        TransacaoResponseDTO nova = transacaoService.criarTransacao(dto, usuarioLogado.getUsuario().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(nova);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar transação", description = "Remove uma transação do usuário logado.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Transação removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada")
    })
    public ResponseEntity<Void> deletar(
            @PathVariable UUID id,
            @AuthenticationPrincipal UsuarioDetailsImpl usuarioLogado) {
        transacaoService.deletar(id, usuarioLogado.getUsuario().getId());
        return ResponseEntity.noContent().build();
    }
}