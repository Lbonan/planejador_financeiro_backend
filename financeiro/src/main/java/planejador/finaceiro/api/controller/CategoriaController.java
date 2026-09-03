package planejador.finaceiro.api.controller;

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
import org.springframework.web.bind.annotation.RestController;
import planejador.finaceiro.api.dto.CategoriaRequestDTO;
import planejador.finaceiro.api.dto.CategoriaResponseDTO;
import planejador.finaceiro.service.CategoriaService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categoria")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Categoria", description = "Endpoints para gerenciamento das categorias de receita e despesa")
public class CategoriaController {
  private CategoriaService categoriaService;

  @GetMapping
  @Operation(summary = "Listar categorias do usuário", description = "Retorna todas as categorias cadastradas para um usuário específico.")
  @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso")
  public ResponseEntity<List<CategoriaResponseDTO>> listar(@PathVariable UUID id) {
      return ResponseEntity.ok(categoriaService.listar(id));
  }

  @GetMapping("/{id}")
  @Operation(summary = "Buscar categoria por ID", description = "Recupera os detalhes de uma categoria específica pelo seu UUID.")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
          @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
  })
  public ResponseEntity<CategoriaResponseDTO> listarPorId(@PathVariable UUID id) {
      return ResponseEntity.ok(categoriaService.buscarPorId(id));
  }

  @PostMapping
  @Operation(summary = "Criar nova categoria", description = "Cadastra uma categoria de receita ou despesa vinculada a um usuário existente.")
  @ApiResponses({
          @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
          @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos (erro de validação dos campos)"),
          @ApiResponse(responseCode = "404", description = "Usuário informado não foi encontrado")
  })
  public ResponseEntity<CategoriaResponseDTO> criar(
          @RequestBody @Valid CategoriaRequestDTO dto,
          @PathVariable UUID id
          ){
      return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.criar(dto, id));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Atualizar categoria", description = "Atualiza os dados de uma categoria existente.")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
          @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
          @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
  })
  public ResponseEntity<CategoriaResponseDTO> atualizar(
          @PathVariable UUID id,
          @RequestBody @Valid CategoriaRequestDTO dto
  ) {
      return ResponseEntity.ok(categoriaService.atualizar(id, dto));
  }

  @DeleteMapping
  @Operation(summary = "Deletar categoria", description = "Remove uma categoria existente do sistema.")
  @ApiResponses({
          @ApiResponse(responseCode = "204", description = "Categoria removida com sucesso"),
          @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
  })
  public ResponseEntity<Void> deletar(@PathVariable UUID id) {
      categoriaService.deletar(id);
      return ResponseEntity.noContent().build();
  }

}
