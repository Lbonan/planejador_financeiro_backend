package planejador.finaceiro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import planejador.finaceiro.api.dto.CartaoRequestDTO;
import planejador.finaceiro.api.dto.CartaoResponseDTO;
import planejador.finaceiro.exceptions.RecursoNaoEncontradoException;
import planejador.finaceiro.model.entity.Cartao;
import planejador.finaceiro.model.entity.Conta;
import planejador.finaceiro.model.entity.Usuario;
import planejador.finaceiro.model.repository.CartaoRepository;
import planejador.finaceiro.model.repository.ContaRepository;
import planejador.finaceiro.model.repository.UsuarioRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartaoService {
    private final CartaoRepository cartaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ContaRepository contaRepository;

    public CartaoResponseDTO criar(CartaoRequestDTO dto, UUID usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário com ID " + usuarioId + " não encontrado"));
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Conta não encontrada"));

        Cartao cartao = new Cartao();
        cartao.setNome(dto.getNome());
        cartao.setLimite(dto.getLimite());
        cartao.setDiaFechamento(dto.getDiaFechamento());
        cartao.setDiaVencimento(dto.getDiaVencimento());
        cartao.setConta(conta);
        cartao.setUsuario(usuario);

        return toResponseDTO(cartaoRepository.save(cartao));
    }

    public List<CartaoResponseDTO> listarPorUsuario(UUID usuarioId) {
        return cartaoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public CartaoResponseDTO buscarPorId(UUID id) {
        Cartao cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cartão não encontrado"));
        return toResponseDTO(cartao);
    }

    public CartaoResponseDTO atualizar(UUID id, CartaoRequestDTO dto) {
        Cartao cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cartão com ID" + id + "não encontrado"));

        cartao.setNome(dto.getNome());
        cartao.setLimite(dto.getLimite());
        cartao.setDiaFechamento(dto.getDiaFechamento());
        cartao.setDiaVencimento(dto.getDiaVencimento());
        return toResponseDTO(cartaoRepository.save(cartao));
    }

    public void remover(UUID id) {
        if (!cartaoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Cartão com ID" + id + "não encontrado");
        }
        cartaoRepository.deleteById(id);
    }

    private CartaoResponseDTO toResponseDTO(Cartao cartao){
        return new CartaoResponseDTO(cartao);
    }
}
