package logica.controladors_pacman;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import logica.Pacman;
import logica.enumeracions.EDireccio;

/**
 *
 * @author oscar
 */
public class ControladorTeclatGrafic implements IControlador, KeyListener{
    private Pacman pacman;
    @Override
    public void assignarPacman(Pacman pacman) {
        this.pacman = pacman;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        EDireccio novaDireccio = EDireccio.QUIET;
        int _teclaPremuda= e.getKeyCode();
        System.out.println(_teclaPremuda);
        if (_teclaPremuda == 38){
            System.out.println("tecla Premuda: Amunt");
            novaDireccio = EDireccio.AMUNT;
        }
        else if (_teclaPremuda == 40){
            System.out.println("tecla Premuda: Avall");
            novaDireccio = EDireccio.AVALL;
        }
        else if (_teclaPremuda == 37){
            System.out.println("tecla Premuda: Esquerra");
            novaDireccio = EDireccio.ESQUERRA;
        }
        else if (_teclaPremuda == 39){
            System.out.println("tecla Premuda: Dreta");
            novaDireccio = EDireccio.DRETA;
        }
        pacman.nouMoviment(novaDireccio);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
