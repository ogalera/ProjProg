package logica.excepcions;

/**
 * @author oscar
 * 
 * @brief
 * Excepció per quan hi ha hagut algun problema en l'evolució de la partida.
 */
public abstract class EPartida extends RuntimeException{
    public EPartida(String msg){
        super(msg);
    }
}
