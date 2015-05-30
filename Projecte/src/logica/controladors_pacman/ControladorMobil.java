package logica.controladors_pacman;

import java.net.ServerSocket;
import logica.Pacman;

/*****************************************************************/
/*                          COMPONENT EXTRA                      */
/*****************************************************************/

/**
 * @author oscar
 * @brief
 * NO ESTÀ ACABAT D'IMPLEMENTAR PER TANT NO ENTRA DINS DEL NOSTRE PROJECTE;
 * PERMET CONTROLAR EN PACMAN DES DE UN DISPOSITIU MOBIL.
 */
public class ControladorMobil implements IControlador{
    private Pacman pacman;
    private final ServerSocket socket;
    
    public ControladorMobil(ServerSocket socket){
        this.socket = socket;
    }
    
    @Override
    public void assignarPacman(Pacman pacman) {
        this.pacman = pacman;
    }
    
}
