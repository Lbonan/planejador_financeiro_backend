package planejador.financeiro.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import planejador.financeiro.model.entity.TipoConta;

import java.math.BigDecimal;
import java.util.UUID;

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
