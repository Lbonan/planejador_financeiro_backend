package planejador.finaceiro.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import planejador.finaceiro.model.entity.TipoCategoria;

@Getter
@Setter
public class CategoriaRequestDTO {
    @NotBlank(message = "nome é obrigatório")
    private String nome;

    @NotNull(message = "Categoria é obrigatório")
    private TipoCategoria tipo;
}
