/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interficie;

import logica.enumeracions.EElement;
import logica.Punt;

/**
 *
 * @author Moises
 */
public interface IPintadorLaberint {
    public void pintarMoviment(Punt a, EElement _a, Punt b, EElement _b);
    public void pintarLaberint();
    
}
