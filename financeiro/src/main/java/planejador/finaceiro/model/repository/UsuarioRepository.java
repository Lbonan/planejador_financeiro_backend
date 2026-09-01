package planejador.finaceiro.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import planejador.finaceiro.model.entity.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
}