package planejador.financeiro.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import planejador.financeiro.api.dto.MetaRequestDTO;
import planejador.financeiro.api.dto.MetaResponseDTO;
import planejador.financeiro.service.MetaService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/metas")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Metas", description = "Endpoints para gerenciamento de metas financeiras")
public class MetaController {

    private final MetaService metaService;

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar metas por usuário", description = "Retorna todas as metas de um usuário específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<List<MetaResponseDTO>> listarPorUsuario(@PathVariable UUID usuarioId) {
        List<MetaResponseDTO> metas = metaService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(metas);
    }

    @GetMapping("/{metaId}")
    @Operation(summary = "Buscar meta por ID", description = "Recupera os detalhes de uma meta específica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Meta encontrada"),
            @ApiResponse(responseCode = "404", description = "Meta não encontrada")
    })
    public ResponseEntity<MetaResponseDTO> buscarPorId(@PathVariable UUID metaId) {
        MetaResponseDTO meta = metaService.buscarPorId(metaId);
        return ResponseEntity.ok(meta);
    }

    @PostMapping
    @Operation(summary = "Criar meta", description = "Cadastra uma nova meta financeira vinculada a um usuário.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Meta criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário informado não foi encontrado")
    })
    public ResponseEntity<MetaResponseDTO> criar(@RequestBody @Valid MetaRequestDTO dto) {
        MetaResponseDTO novaMeta = metaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaMeta);
    }

    @PatchMapping("/{metaId}/aporte")
    @Operation(summary = "Adicionar aporte à meta", description = "Soma um valor ao saldo atual da meta e conclui automaticamente se a meta for atingida.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Aporte adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Valor inválido ou meta não está em andamento"),
            @ApiResponse(responseCode = "404", description = "Meta não encontrada")
    })
    public ResponseEntity<MetaResponseDTO> adicionarAporte(
            @PathVariable UUID metaId,
            @RequestParam @Positive(message = "O valor deve ser positivo") BigDecimal valor) {
        MetaResponseDTO metaAtualizada = metaService.adicionarProgresso(metaId, valor);
        return ResponseEntity.ok(metaAtualizada);
    }

    @DeleteMapping("/{metaId}")
    @Operation(summary = "Deletar meta", description = "Exclui uma meta existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Meta removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Meta não encontrada")
    })
    public ResponseEntity<Void> deletar(@PathVariable UUID metaId) {
        metaService.deletar(metaId);
        return ResponseEntity.noContent().build();
    }
}