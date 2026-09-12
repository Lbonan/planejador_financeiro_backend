package planejador.financeiro.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import planejador.financeiro.api.dto.TransferenciaRequestDTO;
import planejador.financeiro.api.dto.TransferenciaResponseDTO;
import planejador.financeiro.service.TransferenciaService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transferencias")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Transferências", description = "Endpoints para gerenciamento e execução de transferências entre contas")
public class TransferenciaController {

    private final TransferenciaService transferenciaService;

    @GetMapping
    @Operation(
            summary = "Listar transferências",
            description = "Retorna o histórico de todas as transferências cadastradas no sistema."
    )
    @ApiResponse(responseCode = "200", description = "Histórico recuperado com sucesso")
    public ResponseEntity<List<TransferenciaResponseDTO>> listarTodas() {
        List<TransferenciaResponseDTO> transferencias = transferenciaService.listarTodas();
        return ResponseEntity.ok(transferencias);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar transferência por ID",
            description = "Recupera os detalhes de uma transferência pelo seu identificador único (UUID)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transferência encontrada"),
            @ApiResponse(responseCode = "404", description = "Transferência não encontrada")
    })
    public ResponseEntity<TransferenciaResponseDTO> buscarPorId(@PathVariable UUID id) {
        TransferenciaResponseDTO transferencia = transferenciaService.buscarPorId(id);
        return ResponseEntity.ok(transferencia);
    }

    @GetMapping("/conta/{contaId}")
    @Operation(
            summary = "Listar transferências por conta",
            description = "Retorna todas as transferências em que a conta informada participou como origem ou como destino."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de transferências recuperada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    public ResponseEntity<List<TransferenciaResponseDTO>> listarPorConta(@PathVariable UUID contaId) {
        List<TransferenciaResponseDTO> transferencias = transferenciaService.listarPorConta(contaId);
        return ResponseEntity.ok(transferencias);
    }

    @PostMapping
    @Operation(
            summary = "Realizar transferência",
            description = "Transfere um valor monetário entre duas contas distintas com débito, crédito e validação de saldo."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Transferência realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Saldo insuficiente, contas de origem/destino iguais ou dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Conta de origem ou conta de destino não encontrada")
    })
    public ResponseEntity<TransferenciaResponseDTO> transferir(@RequestBody @Valid TransferenciaRequestDTO dto) {
        TransferenciaResponseDTO resposta = transferenciaService.transferir(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }
}