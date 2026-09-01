package planejador.finaceiro.exceptions;

public class RecursoNaoEncontradoException extends RuntimeException{
    public RecursoNaoEncontradoException(String mensagem){
        super(mensagem);
    }
}
