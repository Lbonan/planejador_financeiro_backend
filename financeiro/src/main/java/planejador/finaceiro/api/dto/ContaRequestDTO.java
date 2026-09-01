package planejador.finaceiro.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import planejador.finaceiro.model.entity.TipoConta;

import java.math.BigDecimal;

@Getter
@Setter
public class ContaRequestDTO {
    @NotBlank(message = "Nome da conta é obrigatório")
    private String nome;

    @NotBlank(message = "Banco é obrigatório")
    private String banco;

    @NotNull(message = "Tipo é obrigatório")
    private TipoConta tipoConta;

    private BigDecimal saldo;
}
