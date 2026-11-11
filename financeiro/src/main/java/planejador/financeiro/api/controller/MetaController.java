package planejador.financeiro.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import planejador.financeiro.api.dto.MetaRequestDTO;
import planejador.financeiro.api.dto.MetaResponseDTO;
import planejador.financeiro.security.UsuarioDetailsImpl;
import planejador.financeiro.service.MetaService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/metas")
@RequiredArgsConstructor
@Tag(name = "Metas", description = "Endpoints para gerenciamento de metas financeiras")
public class MetaController {

    private final MetaService metaService;

    @GetMapping
    @Operation(summary = "Listar metas do usuário autenticado", description = "Retorna todas as metas do usuário logado.")
    public ResponseEntity<List<MetaResponseDTO>> listarDoUsuario(
            @AuthenticationPrincipal UsuarioDetailsImpl usuarioLogado) {
        return ResponseEntity.ok(metaService.listarDoUsuario(usuarioLogado.getUsuario().getId()));
    }

    @GetMapping("/{metaId}")
    @Operation(summary = "Buscar meta por ID", description = "Recupera os detalhes de uma meta do usuário logado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Meta encontrada"),
            @ApiResponse(responseCode = "404", description = "Meta não encontrada")
    })
    public ResponseEntity<MetaResponseDTO> buscarPorId(
            @PathVariable UUID metaId,
            @AuthenticationPrincipal UsuarioDetailsImpl usuarioLogado) {
        return ResponseEntity.ok(metaService.buscarPorId(metaId, usuarioLogado.getUsuario().getId()));
    }

    @PostMapping
    @Operation(summary = "Criar meta", description = "Cadastra uma nova meta financeira para o usuário logado.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Meta criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos")
    })
    public ResponseEntity<MetaResponseDTO> criar(
            @RequestBody @Valid MetaRequestDTO dto,
            @AuthenticationPrincipal UsuarioDetailsImpl usuarioLogado) {
        MetaResponseDTO novaMeta = metaService.criar(dto, usuarioLogado.getUsuario().getId());
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
            @RequestParam @Positive(message = "O valor deve ser positivo") BigDecimal valor,
            @AuthenticationPrincipal UsuarioDetailsImpl usuarioLogado) {
        return ResponseEntity.ok(
                metaService.adicionarProgresso(metaId, usuarioLogado.getUsuario().getId(), valor));
    }

    @DeleteMapping("/{metaId}")
    @Operation(summary = "Deletar meta", description = "Exclui uma meta do usuário logado.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Meta removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Meta não encontrada")
    })
    public ResponseEntity<Void> deletar(
            @PathVariable UUID metaId,
            @AuthenticationPrincipal UsuarioDetailsImpl usuarioLogado) {
        metaService.deletar(metaId, usuarioLogado.getUsuario().getId());
        return ResponseEntity.noContent().build();
    }
}