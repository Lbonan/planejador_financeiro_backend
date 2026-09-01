package planejador.finaceiro.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import planejador.finaceiro.model.entity.Transferencia;

import java.util.UUID;

public interface TransferenciaRepository extends JpaRepository<Transferencia, UUID> {
}