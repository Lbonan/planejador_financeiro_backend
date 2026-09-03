package planejador.finaceiro.api.dto;

import lombok.Data;
import planejador.finaceiro.model.entity.Meta;
import planejador.finaceiro.model.entity.StatusMeta;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class MetaResponseDTO {

    private UUID metaId;

    private String nome;

    private BigDecimal valorAtual;

    private BigDecimal valorObjetivo;

    private LocalDate dataCriacao;

    private LocalDate dataAtualizacao;

    private LocalDate dataLimite;

    private StatusMeta statusMeta;

    private UsuarioResponseDTO usuario;

    public MetaResponseDTO(Meta meta) {
        this.metaId = meta.getMetaId();
        this.nome = meta.getNome();
        this.valorAtual = meta.getValorAtual();
        this.valorObjetivo = meta.getValorObjetivo();
        this.dataCriacao = meta.getDataCriacao();
        this.dataAtualizacao = meta.getDataAtualizacao();
        this.dataLimite = meta.getDataLimite();
        this.statusMeta = meta.getStatusMeta();

        if (meta.getUsuario() != null) {
            this.usuario = new UsuarioResponseDTO(meta.getUsuario());
        }
    }
}