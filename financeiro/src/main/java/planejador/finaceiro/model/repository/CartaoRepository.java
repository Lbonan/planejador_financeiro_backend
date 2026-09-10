package planejador.finaceiro.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import planejador.finaceiro.model.entity.Cartao;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, UUID> {
    List<Cartao> findByUsuarioId(UUID usuarioId);
    List<Cartao> findByContaId(UUID cartaoId);
}
