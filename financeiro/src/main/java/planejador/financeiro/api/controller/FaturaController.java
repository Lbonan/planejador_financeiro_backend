package planejador.financeiro.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import planejador.financeiro.api.dto.FaturaRequestDTO;
import planejador.financeiro.api.dto.FaturaResponseDTO;
import planejador.financeiro.service.FaturaService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/faturas")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Faturas", description = "Endpoints para gerenciamento de faturas de cartão de crédito")
public class FaturaController {

    private final FaturaService faturaService;

    @PostMapping
    @Operation(summary = "Criar nova fatura", description = "Cadastra uma fatura vinculada a um cartão existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Fatura criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Cartão informado não foi encontrado")
    })
    public ResponseEntity<FaturaResponseDTO> criar (@Valid @RequestBody FaturaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(faturaService.criar(dto));
    }
    @GetMapping
    @Operation(summary = "Listar faturas de um cartão", description = "Retorna todas as faturas cadastradas para um cartão específico.")
    @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso")
    public ResponseEntity<List<FaturaResponseDTO>> listar(@RequestParam UUID cartaoId){
        return ResponseEntity.ok(faturaService.listarPorCartao(cartaoId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar fatura por ID", description = "Recupera os detalhes de uma fatura específica pelo seu UUID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fatura encontrada"),
            @ApiResponse(responseCode = "404", description = "Fatura não encontrada")
    })
    public ResponseEntity<FaturaResponseDTO> buscar(@PathVariable UUID id){
        return ResponseEntity.ok(faturaService.buscarFatura(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar fatura", description = "Atualiza os dados de uma fatura existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fatura atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Fatura não encontrada")
    })
    public ResponseEntity<FaturaResponseDTO> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody FaturaRequestDTO dto
    ) {
        return ResponseEntity.ok(faturaService.atualizar(dto, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar fatura", description = "Remove uma fatura existente do sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Fatura removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Fatura não encontrada")
    })
    public ResponseEntity<Void> remover(@PathVariable UUID id){
        faturaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
