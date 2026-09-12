package planejador.financeiro.api.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class MetaRequestDTO {

    @NotBlank(message = "O nome da meta é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome da meta deve ter entre 3 e 100 caracteres.")
    private String nome;

    @PositiveOrZero(message = "O valor atual não pode ser negativo.")
    private BigDecimal valorAtual;

    @NotNull(message = "O valor objetivo é obrigatório.")
    @Positive(message = "O valor objetivo deve ser maior que zero.")
    private BigDecimal valorObjetivo;

    @FutureOrPresent(message = "A data limite deve ser a data atual ou uma data futura.")
    private LocalDate dataLimite;

    @NotNull(message = "O ID do usuário é obrigatório.")
    private UUID usuarioId;
}