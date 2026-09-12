package planejador.financeiro.api.dto;

import lombok.Data;
import planejador.financeiro.model.entity.Fatura;
import planejador.financeiro.model.entity.StatusFatura;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class FaturaResponseDTO {
    private UUID id;
    private Integer mes;
    private Integer ano;
    private BigDecimal valor;
    private LocalDate dataVencimento;
    private StatusFatura status;
    private UUID cartaoId;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    public FaturaResponseDTO(Fatura fatura) {
        this.id = fatura.getId();
        this.mes = fatura.getMes();
        this.ano = fatura.getAno();
        this.valor = fatura.getValor();
        this.dataVencimento = fatura.getDataVencimento();
        this.status = fatura.getStatus();
        this.cartaoId = fatura.getCartao().getId();
        this.criadoEm = fatura.getCriadoEm();
        this.atualizadoEm = fatura.getAtualizadoEm();
    }
}
