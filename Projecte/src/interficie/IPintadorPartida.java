package interficie;

import javax.swing.ImageIcon;
import logica.Partida;

/**
 *
 * @author Moises
 * @brief Descriu els metodes necessaris per a que objecte Partida pugui
 * mostrar el desenvolupament d'una partida per pantalla.
 */
public interface IPintadorPartida{
    
    /**
     * @brief Mostra per pantalla la puntuacio d'en Pacman.
     * @post La puntuacio d'en Pacman que es mostra per pantalla esta actualitzada.
     */
    public void pintarPuntsPacman(int punts);
    
    
    /**
     * @brief Mostra per pantalla la puntuacio de l'enemic.
     * @post La puntuacio de l'enemic que es mostra per pantalla esta actualitzada.
     */
    public void pintarPuntsEnemic(int punts);
    
    /**
     * @brief Mostra per pantalla quin tipus de Item hi ha en el Laberint.
     * @post imatge es mostra per panatalla.
     */
    public void pintarItemPartida(ImageIcon imatge);
    
    /**
     * @brief Mostra per pantalla el Item que te en Pacman.
     * @post imatge es mostra per pantalla.
     */
    public void pintarItemPacman(ImageIcon imatge);
    
    /**
     * @brief Mostra per pantalla el Item que l'enemic.
     * @post imatge es mostra per pantalla.
     */
    public void pintarItemEnemic(ImageIcon imatge);
    
    
    /**
     * @brief Construcció de tots els elements necessaris per mostrar partida per pantalla.
     * @post La partida es mostrada per pantalla.
     */
    public void pintarPartida(Partida partida);
    
    /**
     * @brief Mostra per pantalla el resultat de la partida al jugador.
     * @param guanyat Diu si jugador ha guanyat o no la partida
     * @post Es mostra un dialeg per comunicar al jugador si ha guanyat o no.
     */
    public void pintarFinalPartida(boolean guanyat);
    public void pintarIniciPartida();
    public void assignarPartida(Partida partida);
}
