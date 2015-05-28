/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interficie;

import java.awt.Dimension;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import logica.Punt;
import logica.enumeracions.EDireccio;
import logica.enumeracions.EElement;
import logica.laberints.Laberint;

/**
 *
 * @author Moises
 * @brief Descriu els metodes que necessiten els objectes Laberint
 * per a mostrar-se per pantalla.
 * @details Laberint es representa per cel·les, on en cada cel·la  
 */
public interface IPintadorLaberint extends KeyListener{
    
    public void assignarControladorTeclat(KeyListener controlador);
    
    /**
     * @brief Mostra per pantalla el desplaçament de un objecte Personatge. Persona
     * @param pOrigen 
     * @param direccio
     * @param imatge 
     */
    public void pintarMovimentPersonatge(Punt pOrigen, EDireccio direccio, ImageIcon imatge);
    public void pintarMovimentItem(Punt pOrigen, EDireccio direccio, ImageIcon imatge);
    public void pintarNouItem(Punt pNouItem, EElement item);
    public void pintarLaberint(Laberint laberint);
    public Dimension obtenirMidaImatge();
    public void pintarSortida(Punt pSortida);
}
