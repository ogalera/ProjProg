/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interficie;

import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import logica.Punt;
import logica.enumeracions.EDireccio;
import logica.enumeracions.EElement;
import logica.laberints.Laberint;

/**
 *
 * @author Moises
 */
public interface IPintadorLaberint extends KeyListener{
    public void assignarControladorTeclat(KeyListener controlador);
    public void pintarMovimentPersonatge(Punt pOrigen, EDireccio direccio, ImageIcon imatge);
    public void pintarMovimentItem(Punt pOrigen, EDireccio direccio, ImageIcon imatge);
    public void pintarNouItem(Punt pNouItem, EElement item);
    public void pintarLaberint(Laberint laberint);
    public int obtenirMidaImatge();
    public void pintarSortida(Punt pSortida);
}
