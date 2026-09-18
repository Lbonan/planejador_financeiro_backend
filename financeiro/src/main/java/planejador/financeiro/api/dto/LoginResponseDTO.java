package planejador.financeiro.api.dto;

import lombok.Getter;

@Getter
public class LoginResponseDTO {
    private final String token;
    private final String tipo = "Bearer";

    public LoginResponseDTO(String token) {
        this.token = token;
    }
}
