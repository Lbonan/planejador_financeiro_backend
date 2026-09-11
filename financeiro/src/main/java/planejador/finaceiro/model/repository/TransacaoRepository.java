package planejador.finaceiro.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import planejador.finaceiro.model.entity.Transacao;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, UUID> {
    @Query("""
        SELECT t FROM Transacao t 
        JOIN t.conta c 
        JOIN c.usuario u 
        WHERE u.id = :usuarioId 
        ORDER BY t.data DESC
    """)
    List<Transacao> buscarTodasPorUsuarioId(@Param("usuarioId") UUID usuarioId);

    List<Transacao> findByContaIdContaOrderByDataDesc(UUID contaId);
}