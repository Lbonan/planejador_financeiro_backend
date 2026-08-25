package planejador.finaceiro.model.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;



@Getter
@Setter
@Entity
@Table(name = "contas")
public class Conta {
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private UUID id;

   @Column(nullable = false)
   private String nome;

   @Column(nullable = false)
   private String banco;
   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
   private TipoConta tipoConta;

   @Column(name = "saldo_inicial", nullable = false)
   private BigDecimal saldoInicial;

   @Column(name = "criado_em")
   private LocalDateTime criadoEm;

   @Column(name = "atualizado_em")
   private LocalDateTime atualizadoEm;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "usuario_id", nullable = false)
   private Usuario usuario;
}
