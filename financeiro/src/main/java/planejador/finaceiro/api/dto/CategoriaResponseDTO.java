package planejador.finaceiro.api.dto;

import lombok.Data;
import planejador.finaceiro.model.entity.Categoria;
import planejador.finaceiro.model.entity.TipoCategoria;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CategoriaResponseDTO {
    private UUID id;
    private String nome;
    private TipoCategoria tipo;
    private LocalDateTime dataDaCriacao;
    private LocalDateTime dataDaAtualizacao;

    public CategoriaResponseDTO(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
        this.tipo = categoria.getTipo();
        this.dataDaCriacao = categoria.getDataDaCriacao();
        this.dataDaAtualizacao = categoria.getDataDaAtualizacao();
    }
}
