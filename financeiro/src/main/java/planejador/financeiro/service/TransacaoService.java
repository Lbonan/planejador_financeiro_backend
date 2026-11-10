package planejador.financeiro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import planejador.financeiro.api.dto.TransacaoRequestDTO;
import planejador.financeiro.api.dto.TransacaoResponseDTO;
import planejador.financeiro.exceptions.RecursoNaoEncontradoException;
import planejador.financeiro.model.entity.Categoria;
import planejador.financeiro.model.entity.Conta;
import planejador.financeiro.model.repository.ContaRepository;
import planejador.financeiro.model.entity.Transacao;
import planejador.financeiro.model.repository.CategoriaRepository;

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

    public List<TransacaoResponseDTO> listarDoUsuario(UUID usuarioId) {
        return transacaoRepository.listarPorUsuarioId(usuarioId).stream().map(TransacaoResponseDTO::new).toList();
    }


    public TransacaoResponseDTO buscarPorId(UUID id, UUID usuarioId) {
        return transacaoRepository.buscarPorIdEUsuario(id, usuarioId)
                .map(TransacaoResponseDTO::new)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Transação não encontrada."));
    }


    public List<TransacaoResponseDTO> listarPorConta(UUID contaId, UUID usuarioId) {
        if (!contaRepository.existsByIdAndUsuarioId(contaId, usuarioId)) {
            throw new RecursoNaoEncontradoException("Conta não encontrada.");
        }
        return transacaoRepository.listarPorContaId(contaId).stream().map(TransacaoResponseDTO::new).toList();
    }

    public TransacaoResponseDTO criarTransacao(TransacaoRequestDTO dto, UUID usuarioId) {
        Conta conta = contaRepository.findByIdAndUsuarioId(dto.getContaId(), usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Conta não encontrada."));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .filter(c -> c.getUsuario().getId().equals(usuarioId))
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

    public void deletar(UUID id, UUID usuarioId) {
        if (!transacaoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Transação não encontrada.");
        }
        transacaoRepository.buscarPorIdEUsuario(id, usuarioId);
        transacaoRepository.deleteById(id);
    }
}