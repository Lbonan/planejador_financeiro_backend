package planejador.financeiro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import planejador.financeiro.api.dto.TransacaoRequestDTO;
import planejador.financeiro.api.dto.TransacaoResponseDTO;
import planejador.financeiro.exceptions.RecursoNaoEncontradoException;
import planejador.financeiro.model.entity.Categoria;
import planejador.financeiro.model.entity.Conta;
import planejador.financeiro.model.entity.Transacao;
import planejador.financeiro.model.repository.CategoriaRepository;
import planejador.financeiro.model.repository.ContaRepository;
import planejador.financeiro.model.repository.TransacaoRepository;
import planejador.financeiro.model.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ContaRepository contaRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public List<TransacaoResponseDTO> listarTodas() {
        return transacaoRepository.findAll()
                .stream()
                .map(TransacaoResponseDTO::new)
                .toList();
    }

    public TransacaoResponseDTO buscarPorId(UUID id) {
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Transação não encontrada com ID: " + id));
        return new TransacaoResponseDTO(transacao);
    }

    public List<TransacaoResponseDTO> listarPorUsuario(UUID usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + usuarioId);
        }

        return transacaoRepository.listarPorUsuarioId(usuarioId)
                .stream()
                .map(TransacaoResponseDTO::new)
                .toList();
    }

    public List<TransacaoResponseDTO> listarPorConta(UUID contaId) {
        if (!contaRepository.existsById(contaId)) {
            throw new RecursoNaoEncontradoException("Conta não encontrada com ID: " + contaId);
        }

        return transacaoRepository.listarPorContaId(contaId)
                .stream()
                .map(TransacaoResponseDTO::new)
                .toList();
    }

    public TransacaoResponseDTO criarTransacao(TransacaoRequestDTO dto) {
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Conta não encontrada."));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada."));

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
            throw new RecursoNaoEncontradoException("Transação não encontrada.");
        }
        transacaoRepository.deleteById(id);
    }
}