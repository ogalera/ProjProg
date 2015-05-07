package logica.controladors_pacman;

import logica.Pacman;

/**
 *
 * @author oscar
 */
public class ControladorTeclatText implements IControlador{
    private Pacman pacman;

    @Override
    public void assignarPacman(Pacman pacman) {
        this.pacman = pacman;
    }
}
