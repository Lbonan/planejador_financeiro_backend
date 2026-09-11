package planejador.finaceiro.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import planejador.finaceiro.model.entity.Usuario;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {

    private UUID id;

    private String nome;

    private String email;

    private LocalDateTime criadoEm;

    private LocalDateTime atualizadoEm;

    public UsuarioResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.criadoEm = usuario.getCriadoEm();
        this.atualizadoEm = usuario.getAtualizadoEm();
    }
}