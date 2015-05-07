package logica.controladors_pacman;

import java.net.ServerSocket;
import logica.Pacman;

/**
 *
 * @author oscar
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
