package planejador.financeiro.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import planejador.financeiro.model.entity.Categoria;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
    List<Categoria> findByUsuario(UUID usuarioId);
}
