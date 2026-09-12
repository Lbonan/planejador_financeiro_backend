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
import planejador.financeiro.api.dto.CartaoRequestDTO;
import planejador.financeiro.api.dto.CartaoResponseDTO;
import planejador.financeiro.service.CartaoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/cartoes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Cartões", description = "Endpoints para gerenciamento de cartões de crédito")
public class CartaoController {
    private final CartaoService cartaoService;

    @GetMapping
    public ResponseEntity<List<CartaoResponseDTO>> listar(@RequestParam UUID usuarioId){
        return ResponseEntity.ok(cartaoService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartaoResponseDTO> buscar(@PathVariable UUID id){
        return ResponseEntity.ok(cartaoService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Criar novo cartão", description = "Cadastra um cartão de crédito vinculado a uma conta e a um usuário existentes.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cartão criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário ou conta informada não foi encontrada")
    })
    public ResponseEntity<CartaoResponseDTO> criar(
            @Valid @RequestParam CartaoRequestDTO dto,
            @RequestParam UUID usuarioId
            ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartaoService.criar(dto, usuarioId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cartão", description = "Atualiza os dados de um cartão existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cartão atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Cartão não encontrado")
    })
    public ResponseEntity<CartaoResponseDTO> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody CartaoRequestDTO dto
    ){
        return ResponseEntity.ok(cartaoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar cartão", description = "Remove um cartão existente do sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cartão removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cartão não encontrado")
    })
    public ResponseEntity<Void> deletar(@PathVariable UUID id){
        cartaoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
