/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interficie;

import logica.Partida;

/**
 *
 * @author Moises
 */
public interface IPintadorPartida{
    public void pintarPuntsPacman(int punts);
    public void pintarPuntsEnemic(int punts);
    public void pintarPartida(Partida partida);
    public void pintarFinalPartida();
    public void pintarIniciPartida();
}
