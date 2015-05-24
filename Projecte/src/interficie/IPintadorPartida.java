package interficie;

import javax.swing.ImageIcon;
import logica.Partida;

/**
 *
 * @author Moises
 */
public interface IPintadorPartida{
    public void pintarPuntsPacman(int punts);
    public void pintarPuntsEnemic(int punts);
    public void pintarItemPartida(ImageIcon imatge);
    public void pintarItemPacman(ImageIcon imatge);
    public void pintarItemEnemic(ImageIcon imatge);
    public void pintarPartida(Partida partida);
    public void pintarFinalPartida(boolean guanyat);
    public void pintarIniciPartida();
    public void assignarPartida(Partida partida);
}
