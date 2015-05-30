package logica.excepcions;

/**
 * @author oscar
 * 
 * @brief
 * Excepció per quan hi ha algun problema al iniciar la partida.
 */
public class EIniciarPartida extends EPartida{
    /**
     * @pre --
     * @post s'ha creat l'excepció amb msg.
     */
    public EIniciarPartida(String msg){
        super(msg);
    }
}
