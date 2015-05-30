package logica.controladors_pacman;

import logica.Pacman;

/**
 * @author oscar
 * @brief
 * Interficie que ens crea una capa de abstracció de com controlar a en pacman.
 * 
 * És indiferent el dispositiu amb el qual el volem controlar ja que només necessitem
 * saber quina direcció representa l'event sobre el periferic.
 */
public interface IControlador {
    /**
     * @pre --
     * @post em assignat en pacman al controlador "això és necessari perquè el controlador
     * cal que es comuniqui amb en pacman per dir-li la nova direcció que ha de pendre".
     */
    public void assignarPacman(Pacman pacman);
}
