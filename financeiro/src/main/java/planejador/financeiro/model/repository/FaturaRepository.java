package planejador.financeiro.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import planejador.financeiro.model.entity.Fatura;

import java.util.List;
import java.util.UUID;

@Repository
public interface FaturaRepository extends JpaRepository<Fatura, UUID>  {
    List<Fatura> findByCartaoId(UUID cartaoId);
}
