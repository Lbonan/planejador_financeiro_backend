package planejador.finaceiro.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import planejador.finaceiro.model.entity.StatusTransacao;
import planejador.finaceiro.model.entity.TipoTransacao;

import java.math.BigDecimal;

@Data
public class TransacaoRequestDTO {

    @NotNull(message = "O valor da transação é obrigatório.")
    private BigDecimal valor;

    @Size(max = 100)
    private String descricao;

    @NotEmpty(message = "O tipo dessa transação deve ser informado.")
    private TipoTransacao tipoTransacao;

    @NotEmpty(message = "O status dessa transação deve ser informado.")
    private StatusTransacao statusTransacao;

    @NotEmpty(message = "A categoria dessa transação deve ser informada.")
    private CategoriaRequestDTO categoria;

    @NotEmpty(message = "A conta que essa transação pertence deve ser informada.")
    private ContaRequestDTO conta;
}