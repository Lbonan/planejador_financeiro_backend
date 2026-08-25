package planejador.finaceiro.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import planejador.finaceiro.model.entity.Conta;
import java.util.List;
import java.util.UUID;

public interface ContaRepository extends JpaRepository<Conta, UUID> {
    List<Conta> findByUsuario(UUID usuarioId);
}
