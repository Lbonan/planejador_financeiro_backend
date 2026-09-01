package planejador.finaceiro.api.dto;

import lombok.Data;
import planejador.finaceiro.model.entity.Transferencia;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransferenciaResponseDTO {

    private UUID idTransferencia;

    private BigDecimal valor;

    private LocalDateTime dataTransferencia;

    private String descricao;

    private ContaResponseDTO contaOrigem;

    private ContaResponseDTO contaDestino;

    public TransferenciaResponseDTO(Transferencia transferencia) {
        this.idTransferencia = transferencia.getIdTransferencia();
        this.valor = transferencia.getValor();
        this.dataTransferencia = transferencia.getDataTransferencia();
        this.descricao = transferencia.getDescricao();

        if (transferencia.getContaOrigem() != null) {
            this.contaOrigem = new ContaResponseDTO(transferencia.getContaOrigem());
        }
        if (transferencia.getContaDestino() != null) {
            this.contaDestino = new ContaResponseDTO(transferencia.getContaDestino());
        }
    }
}