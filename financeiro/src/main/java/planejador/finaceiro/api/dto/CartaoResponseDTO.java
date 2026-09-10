package planejador.finaceiro.api.dto;

import lombok.Data;
import planejador.finaceiro.model.entity.Cartao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CartaoResponseDTO {
    private UUID id;
    private String nome;
    private BigDecimal limite;
    private Integer diaFechamento;
    private Integer diasVencimento;
    private UUID contaId;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    public CartaoResponseDTO(Cartao cartao) {
        this.id = cartao.getId();
        this.nome = cartao.getNome();
        this.limite = cartao.getLimite();
        this.diaFechamento = cartao.getDiaFechamento();
        this.diasVencimento = cartao.getDiaVencimento();
        this.contaId = cartao.getConta().getId();
        this.criadoEm = cartao.getCriadoEm();
        this.atualizadoEm = cartao.getAtualizadoEm();
    }
}
