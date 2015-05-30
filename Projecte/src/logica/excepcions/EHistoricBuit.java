package logica.excepcions;

/**
 * @author oscar
 * 
 * @brief
 * Excepció que es llença quan es vol accedir al historic i aquest està buit.
 */
public class EHistoricBuit extends EHistoric{
    
    /**
     * @pre --
     * @post em creat l'excepció amb el missatge en que indica que l'historic està buit.
     */
    public EHistoricBuit() {
        super("L'historic de moviments esta buit");
    }
}
