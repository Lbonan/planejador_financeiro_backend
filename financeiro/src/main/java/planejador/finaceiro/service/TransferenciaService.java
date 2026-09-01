package planejador.finaceiro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import planejador.finaceiro.api.dto.TransferenciaRequestDTO;
import planejador.finaceiro.api.dto.TransferenciaResponseDTO;
import planejador.finaceiro.model.entity.Conta;
import planejador.finaceiro.model.entity.Transferencia;
import planejador.finaceiro.model.repository.ContaRepository;
import planejador.finaceiro.model.repository.TransferenciaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferenciaService {

    private final TransferenciaRepository transferenciaRepository;
    private final ContaRepository contaRepository;

    public List<TransferenciaResponseDTO> listarTodas() {
        return transferenciaRepository.findAll()
                .stream()
                .map(TransferenciaResponseDTO::new)
                .toList();
    }

    public TransferenciaResponseDTO buscarPorId(UUID id) {
        Transferencia transferencia = transferenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transferência não encontrada com ID: " + id));
        return new TransferenciaResponseDTO(transferencia);
    }

    public TransferenciaResponseDTO transferir(TransferenciaRequestDTO dto) {
        if (dto.getContaOrigemId().equals(dto.getContaDestinoId())) {
            throw new IllegalArgumentException("A conta de origem e a conta de destino não podem ser iguais.");
        }

        Conta contaOrigem = contaRepository.findById(dto.getContaOrigemId())
                .orElseThrow(() -> new RuntimeException("Conta de origem não encontrada."));

        Conta contaDestino = contaRepository.findById(dto.getContaDestinoId())
                .orElseThrow(() -> new RuntimeException("Conta de destino não encontrada."));

        if (contaOrigem.getSaldo().compareTo(dto.getValor()) < 0) {
            throw new RuntimeException("Saldo insuficiente na conta de origem para realizar a transferência.");
        }

        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(dto.getValor()));
        contaDestino.setSaldo(contaDestino.getSaldo().add(dto.getValor()));

        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);

        Transferencia transferencia = new Transferencia();
        transferencia.setValor(dto.getValor());
        transferencia.setDescricao(dto.getDescricao());
        transferencia.setDataTransferencia(
                dto.getDataTransferencia() != null ? dto.getDataTransferencia() : LocalDateTime.now()
        );
        transferencia.setContaOrigem(contaOrigem);
        transferencia.setContaDestino(contaDestino);

        Transferencia salva = transferenciaRepository.save(transferencia);

        return new TransferenciaResponseDTO(salva);
    }
}