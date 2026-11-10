package planejador.financeiro.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import planejador.financeiro.model.entity.Conta;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContaRepository extends JpaRepository<Conta, UUID> {
    Optional<Conta> findByUsuarioId(UUID id, UUID usuarioId);
    boolean existsByUsuarioId(UUID id, UUID usuarioId);
}
