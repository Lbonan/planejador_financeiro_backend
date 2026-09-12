package planejador.financeiro.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import planejador.financeiro.model.entity.Meta;

import java.util.List;
import java.util.UUID;

public interface MetaRepository extends JpaRepository<Meta, UUID> {
    List<Meta> findByUsuarioId(UUID usuarioId);
}