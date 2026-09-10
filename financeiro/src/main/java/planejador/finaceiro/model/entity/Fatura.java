package planejador.finaceiro.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_faturas")
public class Fatura {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Integer mes;
    private Integer ano;

    private BigDecimal valor;
    private LocalDate dataVencimento;

    @Enumerated(EnumType.STRING)
    private StatusFatura status;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "cartao_id", nullable = false)
    private Cartao cartao;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    @PrePersist
    protected void aoCriar(){
        this.criadoEm = LocalDateTime.now();
        this.atualizadoEm = LocalDateTime.now();
        if (status == null) {
            status = StatusFatura.ABERTA;
        }
        if (valor == null) {
            valor = BigDecimal.ZERO;
        }
    }

    @PreUpdate
    protected void aoAtualizar(){
        this.atualizadoEm = LocalDateTime.now();
    }
}
