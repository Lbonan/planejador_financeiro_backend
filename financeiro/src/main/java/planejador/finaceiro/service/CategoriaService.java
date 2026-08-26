package planejador.finaceiro.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import planejador.finaceiro.model.repository.CategoriaRepository;

@RequiredArgsConstructor
@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;
}
