
package logica.excepcions;

/**
 *
 * @author Moises
 * @brief Excepcio que es llença en les operacions amb objectes Punt
 */
public class EPunt extends RuntimeException{
     /**
     * @brief Constructor de la excepcio
     * @post Excepcio creada
     */
    public EPunt(String missatge){
        super(missatge);
    }
}
