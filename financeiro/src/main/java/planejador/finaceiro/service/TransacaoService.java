package planejador.finaceiro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import planejador.finaceiro.model.repository.TransacaoRepository;

@RequiredArgsConstructor
@Service
public class TransacaoService {

    private TransacaoRepository transacaoRepository;
}