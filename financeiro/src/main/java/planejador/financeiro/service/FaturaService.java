package planejador.financeiro.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import planejador.financeiro.api.dto.FaturaRequestDTO;
import planejador.financeiro.api.dto.FaturaResponseDTO;
import planejador.financeiro.exceptions.RecursoNaoEncontradoException;
import planejador.financeiro.model.entity.Cartao;
import planejador.financeiro.model.entity.Fatura;
import planejador.financeiro.model.repository.CartaoRepository;
import planejador.financeiro.model.repository.FaturaRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FaturaService {
    private final FaturaRepository faturaRepository;
    private final CartaoRepository cartaoRepository;

    public FaturaResponseDTO buscarFatura(UUID id){
        Fatura fatura = faturaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Fatura com ID " + id + " não encontrada"));
        return  toResponseDTO(fatura);
    }
    public List<FaturaResponseDTO> listarPorCartao(UUID cartaoId){
        return faturaRepository.findByCartaoId(cartaoId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    public FaturaResponseDTO criar(FaturaRequestDTO dto){
        Cartao cartao = cartaoRepository.findById(dto.getCartaoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cartão com ID " + dto.getCartaoId() + " não encontrado"));

        Fatura fatura = new Fatura();
        fatura.setMes(dto.getMes());
        fatura.setAno(dto.getAno());
        fatura.setDataVencimento(dto.getDataVencimento());
        fatura.setStatus(dto.getStatus());
        fatura.setCartao(cartao);
        return toResponseDTO(faturaRepository.save(fatura));
    }
    public FaturaResponseDTO atualizar(FaturaRequestDTO dto, UUID id) {
        Fatura fatura = faturaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Fatura com ID" + id + "não encontrada"));
        fatura.setMes(dto.getMes());
        fatura.setAno(dto.getAno());
        fatura.setDataVencimento(dto.getDataVencimento());
        if (dto.getStatus() != null) {
            fatura.setStatus(dto.getStatus());
        }
        return toResponseDTO(faturaRepository.save(fatura));
    }

    public void deletar(UUID id){
        if (!faturaRepository.existsById(id)){
            throw new RecursoNaoEncontradoException("Fatura com ID " + id + " não encontrada" );
        }
        faturaRepository.deleteById(id);
    }

    private FaturaResponseDTO toResponseDTO(Fatura fatura){
        return new FaturaResponseDTO(fatura);
    }
}
