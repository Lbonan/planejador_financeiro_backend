package planejador.finaceiro.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import planejador.finaceiro.model.entity.Conta;
import java.util.List;
import java.util.UUID;

@Repository
public interface ContaRepository extends JpaRepository<Conta, UUID> {
    List<Conta> findByUsuarioId(UUID usuarioId);
}
