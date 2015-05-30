package logica.excepcions;

/**
 * @author oscar
 * Excepció per quan hi ha hagut algun problema amb el laberint.
 */
public class ELaberint extends RuntimeException{
    /**
     * @pre --
     * @post s'ha creat l'excepció amb missatge.
     */
    public ELaberint(String missatge){
        super(missatge);
    }
}
