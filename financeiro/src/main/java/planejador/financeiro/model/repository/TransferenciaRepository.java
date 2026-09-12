package planejador.financeiro.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import planejador.financeiro.model.entity.Transferencia;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, UUID> {
    @Query("SELECT t FROM Transferencia t WHERE t.contaOrigem.idConta = :contaId OR t.contaDestino.idConta = :contaId")
    List<Transferencia> buscarPorConta(@Param("contaId") UUID contaId);
}