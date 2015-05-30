package logica.controladors_pacman;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import logica.Pacman;
import logica.enumeracions.EDireccio;

/**
 * @author oscar
 * 
 * @brief
 * Controlador per dirigir en pacman amb les fletxes del teclat
 */
public class ControladorTeclat implements IControlador, KeyListener{
    private Pacman pacman; /**<pacman, el necessitem per enviar-li la direcció que s'ha presionat*/
    
    @Override
    public void assignarPacman(Pacman pacman) {
        this.pacman = pacman;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        ///S'ha presionat una telca, és una fletxa? i si és una fletxa
        ///quina direcció ens implica?
        EDireccio novaDireccio = EDireccio.QUIET;
        int _teclaPremuda= e.getKeyCode();
        if (_teclaPremuda == 38){
            novaDireccio = EDireccio.AMUNT;
        }
        else if (_teclaPremuda == 40){
            novaDireccio = EDireccio.AVALL;
        }
        else if (_teclaPremuda == 37){
            novaDireccio = EDireccio.ESQUERRA;
        }
        else if (_teclaPremuda == 39){
            novaDireccio = EDireccio.DRETA;
        }
        ///Li enviem la nova direcció a en pacman.
        pacman.nouMoviment(novaDireccio);
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    
}
