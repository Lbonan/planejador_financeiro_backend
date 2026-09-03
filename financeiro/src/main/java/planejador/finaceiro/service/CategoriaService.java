package planejador.finaceiro.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import planejador.finaceiro.api.dto.CategoriaRequestDTO;
import planejador.finaceiro.api.dto.CategoriaResponseDTO;
import planejador.finaceiro.exceptions.RecursoNaoEncontradoException;
import planejador.finaceiro.model.entity.Categoria;
import planejador.finaceiro.model.entity.Usuario;
import planejador.finaceiro.model.repository.CategoriaRepository;
import planejador.finaceiro.model.repository.UsuarioRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public CategoriaResponseDTO criar(CategoriaRequestDTO dto, UUID usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário com ID " + usuarioId + "não encontrado"));
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        categoria.setTipo(dto.getTipo());
        categoria.setUsuario(usuario);

        Categoria categoriaSalva = categoriaRepository.save(categoria);
        return toResponseDTO(categoriaSalva);
    }

    public List<CategoriaResponseDTO> listar(UUID usuarioId) {
        return categoriaRepository.findByUsuario(usuarioId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public CategoriaResponseDTO buscarPorId(UUID id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria com ID " + id + "não encontrado"));
        return toResponseDTO(categoria);
    }

    public CategoriaResponseDTO atualizar(UUID id, CategoriaRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria com ID " + id + "não encontrado"));
        categoria.setNome(dto.getNome());
        categoria.setTipo(dto.getTipo());

        Categoria atualizada = categoriaRepository.save(categoria);
        return toResponseDTO(atualizada);
    }

    public void deletar(UUID id) {
    if (!categoriaRepository.existsById(id)) {
        throw new RecursoNaoEncontradoException("Categoria com ID " + id + "não encontrado");
    }
        categoriaRepository.deleteById(id);
    }

    private CategoriaResponseDTO toResponseDTO(Categoria categoria){
        return new CategoriaResponseDTO(categoria);
    }
}
