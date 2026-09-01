package planejador.finaceiro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import planejador.finaceiro.api.dto.TransacaoRequestDTO;
import planejador.finaceiro.api.dto.TransacaoResponseDTO;
import planejador.finaceiro.model.entity.Categoria;
import planejador.finaceiro.model.entity.Conta;
import planejador.finaceiro.model.entity.TipoTransacao;
import planejador.finaceiro.model.entity.Transacao;
import planejador.finaceiro.model.repository.CategoriaRepository;
import planejador.finaceiro.model.repository.ContaRepository;
import planejador.finaceiro.model.repository.TransacaoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ContaRepository contaRepository;
    private final CategoriaRepository categoriaRepository;

    public List<TransacaoResponseDTO> listarTodas() {
        return transacaoRepository.findAll()
                .stream()
                .map(TransacaoResponseDTO::new)
                .toList();
    }

    public TransacaoResponseDTO buscarPorId(UUID id) {
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada com ID: " + id));
        return new TransacaoResponseDTO(transacao);
    }

    public TransacaoResponseDTO criarTransacao(TransacaoRequestDTO dto) {
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new RuntimeException("Conta não encontrada."));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada."));

        Transacao transacao = new Transacao();
        transacao.setValor(dto.getValor());
        transacao.setDescricao(dto.getDescricao());
        transacao.setTipoTransacao(dto.getTipoTransacao());
        transacao.setStatusTransacao(dto.getStatusTransacao());
        transacao.setData(dto.getData() != null ? dto.getData() : LocalDateTime.now());
        transacao.setConta(conta);
        transacao.setCategoria(categoria);

        Transacao salva = transacaoRepository.save(transacao);
        return new TransacaoResponseDTO(salva);
    }

    public void deletar(UUID id) {
        if (!transacaoRepository.existsById(id)) {
            throw new RuntimeException("Transação não encontrada.");
        }
        transacaoRepository.deleteById(id);
    }
}