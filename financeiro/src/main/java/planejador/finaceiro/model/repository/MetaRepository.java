package planejador.finaceiro.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import planejador.finaceiro.model.entity.Meta;

import java.util.UUID;

public interface MetaRepository extends JpaRepository<Meta, UUID> {
}