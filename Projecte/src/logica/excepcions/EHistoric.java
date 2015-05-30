package logica.excepcions;

/**
 * @author oscar
 * Excepció que indica que hi ha hagut un problema amb l'historic d'algun personatge.
 */
public class EHistoric extends RuntimeException{
    
    /**
     * @pre --
     * @post s'ha creat l'excepció amb missatge.
     */
    public EHistoric(String missatge){
        super(missatge);
    }
}
