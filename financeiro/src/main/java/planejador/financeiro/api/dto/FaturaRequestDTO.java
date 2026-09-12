package planejador.financeiro.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import planejador.financeiro.model.entity.StatusFatura;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class FaturaRequestDTO {
    @NotNull(message = "Mês é obrigatório")
    @Min(value = 1, message = "Mês deve ser entre 1 e 12")
    @Max(value = 12, message = "Mês deve ser entre 1 e 12")
    private Integer mes;

    @NotNull(message = "Ano é obrigatório")
    private Integer ano;

    @NotNull(message = "Data de vencimento é obrigatória")
    private LocalDate dataVencimento;

    private StatusFatura status;

    @NotNull(message = "Cartão é obrigatória")
    private UUID cartaoId;
}
