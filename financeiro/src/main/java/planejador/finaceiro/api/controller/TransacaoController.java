package planejador.finaceiro.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import planejador.finaceiro.api.dto.TransacaoRequestDTO;
import planejador.finaceiro.api.dto.TransacaoResponseDTO;
import planejador.finaceiro.service.TransacaoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transacoes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Transações", description = "Endpoints para gerenciamento de transações financeiras")
public class TransacaoController {

    private final TransacaoService transacaoService;

    @GetMapping
    @Operation(summary = "Listar transações", description = "Retorna uma lista com todas as transações cadastradas.")
    @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso")
    public ResponseEntity<List<TransacaoResponseDTO>> listarTodas() {
        List<TransacaoResponseDTO> transacoes = transacaoService.listarTodas();
        return ResponseEntity.ok(transacoes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar transação por ID", description = "Recupera os detalhes de uma transação específica pelo seu UUID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transação encontrada"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada")
    })
    public ResponseEntity<TransacaoResponseDTO> buscarPorId(@PathVariable UUID id) {
        TransacaoResponseDTO transacao = transacaoService.buscarPorId(id);
        return ResponseEntity.ok(transacao);
    }

    @PostMapping
    @Operation(
            summary = "Criar nova transação",
            description = "Cadastra uma transação financeira vinculada a uma conta e categoria existentes."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Transação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos (erro de validação dos campos)"),
            @ApiResponse(responseCode = "404", description = "Conta ou categoria informada não foi encontrada")
    })
    public ResponseEntity<TransacaoResponseDTO> criar(@RequestBody @Valid TransacaoRequestDTO dto) {
        TransacaoResponseDTO novaTransacao = transacaoService.criarTransacao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaTransacao);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar transação", description = "Remove uma transação existente do sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Transação removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada")
    })
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        transacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}