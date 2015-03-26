package projecte.excepcions;

/**
 * @author oscar
 * DECLARACIÓ DE INTENCIONS DE L'EXCEPCIÓ
 * Llençarem aquesta excepció sempre que hi hagui un incompliment
 * en el laberint;
 */
public class LaberintException extends RuntimeException{
    public LaberintException(String missatge){
        super(missatge);
    }
}
