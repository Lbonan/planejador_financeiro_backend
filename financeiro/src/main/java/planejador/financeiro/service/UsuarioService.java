package planejador.financeiro.service;

import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import planejador.financeiro.api.dto.UsuarioRequestDTO;
import planejador.financeiro.api.dto.UsuarioResponseDTO;
import planejador.financeiro.exceptions.RecursoNaoEncontradoException;
import planejador.financeiro.model.entity.Usuario;
import planejador.financeiro.model.repository.UsuarioRepository;

import java.util.List;
import java.util.UUID;

@Service
@Setter
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Já existe um usuário cadastrado com esse e-mail.");
        }

        Usuario usuario = new Usuario();

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return converterParaResponse(usuarioSalvo);
    }

    public UsuarioResponseDTO buscarPorId(UUID id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));

        return converterParaResponse(usuario);
    }

    public List<UsuarioResponseDTO> listarTodos() {

        return usuarioRepository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public UsuarioResponseDTO atualizar(UUID id, UsuarioRequestDTO dto) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);

        return converterParaResponse(usuarioAtualizado);
    }

    public void excluir(UUID id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));

        usuarioRepository.delete(usuario);
    }

    private UsuarioResponseDTO converterParaResponse(Usuario usuario) {

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCriadoEm(),
                usuario.getAtualizadoEm()
        );
    }
}