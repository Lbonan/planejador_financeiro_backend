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

    public List<MetaResponseDTO> listarDoUsuario(UUID usuarioId) {
        return metaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(MetaResponseDTO::new)
                .toList();
    }

    public MetaResponseDTO buscarPorId(UUID metaId, UUID usuarioId) {
        return new MetaResponseDTO(buscarDoUsuario(metaId, usuarioId));
    }

    public MetaResponseDTO criar(MetaRequestDTO dto, UUID usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));

        Meta meta = new Meta();
        meta.setNome(dto.getNome());
        meta.setValorAtual(dto.getValorAtual());
        meta.setValorObjetivo(dto.getValorObjetivo());
        meta.setDataLimite(dto.getDataLimite());
        meta.setUsuario(usuario);

        if (dto.getValorAtual() != null && dto.getValorAtual().compareTo(dto.getValorObjetivo()) >= 0) {
            meta.setStatusMeta(StatusMeta.CONCLUIDA);
        }

        return new MetaResponseDTO(metaRepository.save(meta));
    }

    public MetaResponseDTO adicionarProgresso(UUID metaId, UUID usuarioId, BigDecimal valorAporte) {
        if (valorAporte == null || valorAporte.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do aporte deve ser positivo.");
        }

        Meta meta = buscarDoUsuario(metaId, usuarioId);

        if (meta.getStatusMeta() != StatusMeta.EM_ANDAMENTO) {
            throw new IllegalArgumentException("Aportes só podem ser feitos em metas com status EM_ANDAMENTO.");
        }

        BigDecimal novoValor = meta.getValorAtual().add(valorAporte);
        meta.setValorAtual(novoValor);

        if (novoValor.compareTo(meta.getValorObjetivo()) >= 0) {
            meta.setStatusMeta(StatusMeta.CONCLUIDA);
        }

        return new MetaResponseDTO(metaRepository.save(meta));
    }

    public void deletar(UUID metaId, UUID usuarioId) {
        metaRepository.delete(buscarDoUsuario(metaId, usuarioId));
    }

    private Meta buscarDoUsuario(UUID metaId, UUID usuarioId) {
        return metaRepository.findByMetaIdAndUsuarioId(metaId, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Meta não encontrada com ID: " + metaId));
    }
}