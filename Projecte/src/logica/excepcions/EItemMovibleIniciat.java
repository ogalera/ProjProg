package logica.excepcions;

/**
 * @author oscar
 * @brief
 * Excepció per quan s'ha iniciat un item que ja estava iniciat.
 */
public class EItemMovibleIniciat extends EPartida{
    
    /**
     * @pre --
     * @post s'ha creat l'excepció amb nomItem.
     */
    public EItemMovibleIniciat(String nomItem){
        super("L'item movible "+nomItem+" ja estava iniciat");
    }
}
