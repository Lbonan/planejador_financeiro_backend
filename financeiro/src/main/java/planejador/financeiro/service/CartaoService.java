package planejador.financeiro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import planejador.financeiro.api.dto.CartaoRequestDTO;
import planejador.financeiro.api.dto.CartaoResponseDTO;
import planejador.financeiro.exceptions.RecursoNaoEncontradoException;
import planejador.financeiro.model.entity.Cartao;
import planejador.financeiro.model.entity.Conta;
import planejador.financeiro.model.entity.Usuario;
import planejador.financeiro.model.repository.CartaoRepository;
import planejador.financeiro.model.repository.ContaRepository;
import planejador.financeiro.model.repository.UsuarioRepository;

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
