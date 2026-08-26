package planejador.finaceiro.api.dto;

import lombok.Data;
import planejador.finaceiro.model.entity.Conta;
import planejador.finaceiro.model.entity.TipoConta;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ContaResponseDTO {
    private UUID id;
    private String nome;
    private String banco;
    private TipoConta tipoConta;
    private BigDecimal saldoInicial;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    public ContaResponseDTO(Conta conta) {
        this.id = conta.getId();
        this.nome = conta.getNome();
        this.banco = conta.getBanco();
        this.tipoConta = conta.getTipoConta();
        this.saldoInicial = conta.getSaldoInicial();
        this.criadoEm = conta.getCriadoEm();
        this.atualizadoEm = conta.getAtualizadoEm();
    }
}
