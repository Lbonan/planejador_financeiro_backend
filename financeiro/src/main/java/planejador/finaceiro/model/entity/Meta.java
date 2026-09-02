package planejador.finaceiro.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_metas")
public class Meta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID metaId;

    private String nome;

    private BigDecimal valorAtual;

    private BigDecimal valorObjetivo;

    private LocalDate dataCriacao;

    private LocalDate dataAtualizacao;

    private LocalDate dataLimite;

    @Enumerated(EnumType.STRING)
    private StatusMeta statusMeta;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDate.now();
        this.dataAtualizacao = LocalDate.now();
        if (this.valorAtual == null) {
            this.valorAtual = BigDecimal.ZERO;
        }
        if (this.statusMeta == null) {
            this.statusMeta = StatusMeta.EM_ANDAMENTO;
        }
    }
}