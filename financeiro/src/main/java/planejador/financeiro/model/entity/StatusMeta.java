package planejador.financeiro.model.entity;

import lombok.Getter;

@Getter
public enum StatusMeta {

    EM_ANDAMENTO("Em Andamento"),
    CONCLUIDA("Concluída"),
    CANCELADA("Cancelada"),
    EXPIRADA("Expirada");

    private final String descricao;

    StatusMeta(String descricao) {
        this.descricao = descricao;
    }
}