
package logica.excepcions;

/**
 *
 * @author Moises
 * @brief Excepcio que es llença en la cerca de Camins
 */
public class ExceptionBuscadorCamins extends RuntimeException {
    /**
     * @brief Constructor de la excepcio
     * @post Excepcio creada
     */
    public ExceptionBuscadorCamins(String msg){
        super(msg);
    }
    
}
