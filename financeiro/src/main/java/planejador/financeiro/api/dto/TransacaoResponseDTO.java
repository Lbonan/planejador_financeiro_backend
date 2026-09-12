package planejador.financeiro.api.dto;

import lombok.Data;
import planejador.financeiro.model.entity.StatusTransacao;
import planejador.financeiro.model.entity.TipoTransacao;
import planejador.financeiro.model.entity.Transacao;

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

        if (transacao.getCategoria() != null) {
            this.categoria = new CategoriaResponseDTO(transacao.getCategoria());
        }
        if (transacao.getConta() != null) {
            this.conta = new ContaResponseDTO(transacao.getConta());
        }
    }
}