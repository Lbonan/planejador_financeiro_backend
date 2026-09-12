package planejador.financeiro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import planejador.financeiro.api.dto.MetaRequestDTO;
import planejador.financeiro.api.dto.MetaResponseDTO;
import planejador.financeiro.exceptions.RecursoNaoEncontradoException;
import planejador.financeiro.model.entity.Meta;
import planejador.financeiro.model.entity.StatusMeta;
import planejador.financeiro.model.entity.Usuario;
import planejador.financeiro.model.repository.MetaRepository;
import planejador.financeiro.model.repository.UsuarioRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MetaService {

    private final MetaRepository metaRepository;
    private final UsuarioRepository usuarioRepository;

    public List<MetaResponseDTO> listarPorUsuario(UUID usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + usuarioId);
        }
        return metaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(MetaResponseDTO::new)
                .toList();
    }

    public MetaResponseDTO buscarPorId(UUID metaId) {
        Meta meta = metaRepository.findById(metaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Meta não encontrada com ID: " + metaId));
        return new MetaResponseDTO(meta);
    }

    public MetaResponseDTO criar(MetaRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + dto.getUsuarioId()));

        Meta meta = new Meta();
        meta.setNome(dto.getNome());
        meta.setValorAtual(dto.getValorAtual());
        meta.setValorObjetivo(dto.getValorObjetivo());
        meta.setDataLimite(dto.getDataLimite());
        meta.setUsuario(usuario);

        if (dto.getValorAtual() != null && dto.getValorAtual().compareTo(dto.getValorObjetivo()) >= 0) {
            meta.setStatusMeta(StatusMeta.CONCLUIDA);
        }

        Meta metaSalva = metaRepository.save(meta);
        return new MetaResponseDTO(metaSalva);
    }

    public MetaResponseDTO adicionarProgresso(UUID metaId, BigDecimal valorAporte) {
        if (valorAporte == null || valorAporte.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do aporte deve ser positivo.");
        }

        Meta meta = metaRepository.findById(metaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Meta não encontrada com ID: " + metaId));

        if (meta.getStatusMeta() != StatusMeta.EM_ANDAMENTO) {
            throw new IllegalStateException("Aportes só podem ser feitos em metas com status EM_ANDAMENTO.");
        }

        BigDecimal novoValor = meta.getValorAtual().add(valorAporte);
        meta.setValorAtual(novoValor);

        if (novoValor.compareTo(meta.getValorObjetivo()) >= 0) {
            meta.setStatusMeta(StatusMeta.CONCLUIDA);
        }

        Meta metaAtualizada = metaRepository.save(meta);
        return new MetaResponseDTO(metaAtualizada);
    }

    public void deletar(UUID metaId) {
        if (!metaRepository.existsById(metaId)) {
            throw new RecursoNaoEncontradoException("Meta não encontrada com ID: " + metaId);
        }
        metaRepository.deleteById(metaId);
    }
}