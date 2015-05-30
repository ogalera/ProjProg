package logica.excepcions;

/**
 * @author oscar
 * 
 * @brief
 * Excepció per quan hi ha algun problema al finalitzar la partida.
 */
public class EFinalitzarPartida extends EPartida{
    
    /**
     * @pre --
     * @post s'ha creat l'excepció amb msg.
     */
    public EFinalitzarPartida(String msg){
        super(msg);
    }
}
