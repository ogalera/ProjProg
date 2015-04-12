package logica.excepcions;

/**
 * @author oscar
 * DECLARACIÓ DE INTENCIONS DE L'EXCEPCIÓ
 * Llençarem aquesta excepció sempre que hi hagui un incompliment
 * en el laberint;
 */
public class ELaberint extends RuntimeException{
    public ELaberint(String missatge){
        super(missatge);
    }
}
