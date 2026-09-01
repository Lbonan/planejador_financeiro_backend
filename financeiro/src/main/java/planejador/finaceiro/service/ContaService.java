package planejador.finaceiro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import planejador.finaceiro.model.repository.ContaRepository;

@RequiredArgsConstructor
@Service
public class ContaService {
    private final ContaRepository contaRepository;

}
