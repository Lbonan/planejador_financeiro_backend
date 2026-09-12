package planejador.financeiro.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import planejador.financeiro.model.entity.StatusTransacao;
import planejador.financeiro.model.entity.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransacaoRequestDTO {

    @NotNull(message = "O valor da transação é obrigatório.")
    @Positive(message = "O valor deve ser positivo.")
    private BigDecimal valor;

    @Size(max = 100, message = "A descrição deve ter no máximo 100 caracteres.")
    private String descricao;

    private LocalDateTime data;

    @NotNull(message = "O tipo dessa transação deve ser informado.")
    private TipoTransacao tipoTransacao;

    @NotNull(message = "O status dessa transação deve ser informado.")
    private StatusTransacao statusTransacao;

    @NotNull(message = "A categoria dessa transação deve ser informada.")
    private UUID categoriaId;

    @NotNull(message = "A conta que essa transação pertence deve ser informada.")
    private UUID contaId;
}