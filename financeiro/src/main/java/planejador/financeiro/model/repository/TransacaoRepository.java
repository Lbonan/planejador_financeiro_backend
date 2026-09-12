package planejador.financeiro.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import planejador.financeiro.model.entity.Transacao;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, UUID> {

    @Query("SELECT t FROM Transacao t WHERE t.conta.id = :contaId ORDER BY t.data DESC")
    List<Transacao> listarPorContaId(@Param("contaId") UUID contaId);


    @Query("SELECT t FROM Transacao t WHERE t.conta.usuario.id = :usuarioId ORDER BY t.data DESC")
    List<Transacao> listarPorUsuarioId(@Param("usuarioId") UUID usuarioId);
}