package planejador.financeiro.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(nullable = false)
    private LocalDateTime atualizadoEm;


    // RELACIONAMENTOS

    @OneToMany(mappedBy = "usuario")
    private List<Conta> contas = new ArrayList<>();

    @OneToMany(mappedBy = "usuario")
    private List<Categoria> categorias = new ArrayList<>();

    @OneToMany(mappedBy = "usuario")
    private List<Meta> metas = new ArrayList<>();

    @OneToMany(mappedBy = "usuario")
    private List<Cartao> cartoes = new ArrayList<>();

    @OneToMany(mappedBy = "usuario")
    private List<Transferencia> transferencias = new ArrayList<>();


    @PrePersist
    public void prePersist() {
        criadoEm = LocalDateTime.now();
        atualizadoEm = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        atualizadoEm = LocalDateTime.now();
    }
}