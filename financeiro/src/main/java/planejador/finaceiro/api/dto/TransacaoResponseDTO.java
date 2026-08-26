package planejador.finaceiro.api.dto;

import lombok.Data;
import planejador.finaceiro.model.entity.StatusTransacao;
import planejador.finaceiro.model.entity.TipoTransacao;
import planejador.finaceiro.model.entity.Transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransacaoResponseDTO {

    private UUID transacaoId;

    private BigDecimal valor;

    private String descricao;

    private LocalDateTime data;

    private TipoTransacao tipoTransacao;

    private StatusTransacao statusTransacao;

    private CategoriaResponseDTO categoria;

    private ContaResponseDTO conta;

    public TransacaoResponseDTO(Transacao transacao) {
        this.transacaoId = transacao.getIdTransacao();
        this.valor = transacao.getValor();
        this.descricao = transacao.getDescricao();
        this.data = transacao.getData();
        this.tipoTransacao = transacao.getTipoTransacao();
        this.statusTransacao = transacao.getStatusTransacao();
    }
}