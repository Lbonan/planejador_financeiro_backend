package planejador.financeiro.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import planejador.financeiro.api.dto.ContaRequestDTO;
import planejador.financeiro.api.dto.ContaResponseDTO;
import planejador.financeiro.exceptions.RecursoNaoEncontradoException;
import planejador.financeiro.model.entity.Conta;
import planejador.financeiro.model.entity.Usuario;
import planejador.financeiro.model.repository.ContaRepository;
import planejador.financeiro.model.repository.UsuarioRepository;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final UsuarioRepository usuarioRepository;


    public ContaResponseDTO criarConta(ContaRequestDTO dto, UUID usuarioId){
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário com id " + usuarioId + "não encontrado"));

        Conta conta = new Conta();
        conta.setNome(dto.getNome());
        conta.setBanco(dto.getBanco());
        conta.setTipoConta(dto.getTipoConta());
        conta.setSaldo(dto.getSaldo());
        conta.setUsuario(usuario);

        Conta salva = contaRepository.save(conta);
        return toResponseDTO(salva);

    }

    private Conta buscarUsuario(UUID id,  UUID usuarioId){
        return contaRepository.findByUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Conta com id " + id + " não encontrada"));
    }

    public ContaResponseDTO buscarPorId(UUID id,  UUID usuarioId){
        return new ContaResponseDTO(buscarUsuario(id, usuarioId));
    }

    public ContaResponseDTO atualizarConta(ContaRequestDTO dto, UUID id, UUID usuarioId) {
        Conta conta = buscarUsuario(id, usuarioId);
        conta.setNome(dto.getNome());
        conta.setBanco(dto.getBanco());
        conta.setTipoConta(dto.getTipoConta());
        conta.setSaldo(dto.getSaldo());
        return toResponseDTO(contaRepository.save(conta));
    }


    public void deletarConta(UUID id, UUID usuarioId) {
        contaRepository.delete(buscarUsuario(id, usuarioId));
    }

    private ContaResponseDTO toResponseDTO(Conta conta){
        return new ContaResponseDTO(conta);
    }

}
