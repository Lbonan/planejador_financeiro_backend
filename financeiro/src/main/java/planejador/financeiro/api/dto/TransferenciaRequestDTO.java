package planejador.financeiro.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransferenciaRequestDTO {

    @NotNull(message = "O valor da transferência é obrigatório.")
    @Positive(message = "O valor deve ser maior que zero.")
    private BigDecimal valor;

    @Size(max = 150, message = "A descrição deve ter no máximo 150 caracteres.")
    private String descricao;

    private LocalDateTime dataTransferencia;

    @NotNull(message = "O ID da conta de origem é obrigatório.")
    private UUID contaOrigemId;

    @NotNull(message = "O ID da conta de destino é obrigatório.")
    private UUID contaDestinoId;
}