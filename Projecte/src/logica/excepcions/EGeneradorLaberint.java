package logica.excepcions;

/**
 * @author oscar
 * Excepció per quan hi ha hagut algun problema en el procés de generació de laberints.
 */
public class EGeneradorLaberint extends RuntimeException{
    
    /**
     * @pre --
     * @post s'ha creat l'excepció amb missatge.
     */
    public EGeneradorLaberint(String missatge){
        super(missatge);
    }
}
