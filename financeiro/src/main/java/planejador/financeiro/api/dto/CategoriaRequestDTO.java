package planejador.financeiro.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import planejador.financeiro.model.entity.TipoCategoria;

@Getter
@Setter
public class CategoriaRequestDTO {
    @NotBlank(message = "nome é obrigatório")
    private String nome;

    @NotNull(message = "Categoria é obrigatório")
    private TipoCategoria tipo;
}
