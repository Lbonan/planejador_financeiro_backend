package planejador.financeiro.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class CartaoRequestDTO {
    @NotBlank(message = "Nome do cartão é obrigatório")
    private String nome;

    @NotNull(message = "Limite é obrigatório")
    @Positive(message = "Limite deve ser maior que zero")
    private BigDecimal limite;

    @NotNull(message = "Dia de fechamento é obrigatório")
    @Min(value = 1, message = "Dia de fechamento deve ser entre 1 e 31")
    @Max(value = 31, message = "Dia de fechamento deve ser entre 1 e 31")
    private Integer diaFechamento;

    @NotNull(message = "Dia de vencimento é obrigatório")
    @Min(value = 1, message = "Dia de vencimento deve ser entre 1 e 31")
    @Max(value = 31, message = "Dia de vencimento deve ser entre 1 e 31")
    private Integer diaVencimento;

    @NotNull(message = "Conta é obrigatória")
    private UUID contaId;
}
