/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interficie;

import java.awt.event.KeyListener;
import logica.enumeracions.EElement;
import logica.Punt;
import logica.enumeracions.EDireccio;
import logica.laberints.Laberint;

/**
 *
 * @author Moises
 */
public interface IPintadorLaberint extends KeyListener{
    public void assignarControladorTeclat(KeyListener controlador);
    public void pintarMoviment(Punt pOrigen, EElement eOrigen, EDireccio direccio, EElement eDesti);
    public void pintarNouItem(Punt pNouItem, EElement nouItem);
    public void pintarLaberint(Laberint laberint);
}
