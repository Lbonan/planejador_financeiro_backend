package planejador.finaceiro.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import planejador.finaceiro.model.entity.Transacao;

import java.util.UUID;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, UUID> {
}