
package logica.excepcions;

/**
 *
 * @author Moises
 * @brief Excepcio que es llença en la cerca de Camins
 */
public class EBuscadorCamins extends RuntimeException {
    /**
     * @brief Constructor de la excepcio
     * @post Excepcio creada
     */
    public EBuscadorCamins(String msg){
        super(msg);
    }
    
}
