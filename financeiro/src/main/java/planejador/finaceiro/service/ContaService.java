package planejador.finaceiro.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import planejador.finaceiro.api.dto.ContaResponseDTO;
import planejador.finaceiro.exceptions.RecursoNaoEncontradoException;
import planejador.finaceiro.model.entity.Conta;
import planejador.finaceiro.model.entity.Usuario;
import planejador.finaceiro.model.repository.ContaRepository;
import planejador.finaceiro.model.repository.UsuarioRepository;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final UsuarioRepository usuarioRepository;


    public ContaResponseDTO criarConta(ContaResponseDTO dto, UUID usuarioId){
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário com id " + usuarioId + "não encontrado"));

        Conta conta = new Conta();
        conta.setNome(dto.getNome());
        conta.setBanco(dto.getBanco());
        conta.setTipoConta(dto.getTipoConta());
        conta.setSaldoInicial(dto.getSaldoInicial());
        conta.setUsuario(usuario);

        Conta salva = contaRepository.save(conta);
        return toResponseDTO(salva);

    }

    public ContaResponseDTO buscarPorId(UUID id) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Conta com id " + id + "não encontrado"));
        return new ContaResponseDTO(conta);
    }


    public ContaResponseDTO atualizarConta(ContaResponseDTO dto, UUID id){
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Conta com id" + id + "não encontrado"));

        conta.setNome(dto.getNome());
        conta.setBanco(dto.getBanco());
        conta.setTipoConta(dto.getTipoConta());
        conta.setSaldoInicial(dto.getSaldoInicial());

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
