package planejador.financeiro.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_transferencias")
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idTransferencia;

    private BigDecimal valor;

    private LocalDateTime dataTransferencia;

    private String descricao;

    @ManyToOne
    @JoinColumn(name = "conta_origem_id")
    private Conta contaOrigem;

    @ManyToOne
    @JoinColumn(name = "conta_destino_id")
    private Conta contaDestino;
}