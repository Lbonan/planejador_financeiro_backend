package planejador.financeiro.exceptions;

public class ConflitoException extends RuntimeException{
    public ConflitoException(String mensagem){
        super(mensagem);
    }
}
