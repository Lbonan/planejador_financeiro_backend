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

    public List<ContaResponseDTO> listarPorUsuario(UUID usuarioId){
        return contaRepository.findByUsuarioId(usuarioId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ContaResponseDTO buscarPorId(UUID id) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Conta com id " + id + "não encontrado"));
        return new ContaResponseDTO(conta);
    }


    public ContaResponseDTO atualizarConta(ContaRequestDTO dto, UUID id){
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Conta com id" + id + "não encontrado"));

        conta.setNome(dto.getNome());
        conta.setBanco(dto.getBanco());
        conta.setTipoConta(dto.getTipoConta());
        conta.setSaldo(dto.getSaldo());

        Conta atualizada = contaRepository.save(conta);
        return toResponseDTO(atualizada);
    }

    public void deletarConta(UUID id){
        if(!contaRepository.existsById(id)){
            throw new RecursoNaoEncontradoException("Conta com id " + id + "não encontrado");
        }
        contaRepository.deleteById(id);
    }

    private ContaResponseDTO toResponseDTO(Conta conta){
        return new ContaResponseDTO(conta);
    }

}
