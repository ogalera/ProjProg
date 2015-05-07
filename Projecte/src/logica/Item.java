/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import logica.laberints.Laberint;
import logica.enumeracions.EDireccio;
import logica.enumeracions.EElement;

/**
 *
 * @author oscar
 */
public class Item extends ItemMovible {
    private EElement tipusElement;
    
    public Item(Laberint laberint, Punt inici){
        super(laberint, inici);
    }

    @Override
    public EDireccio calcularMoviment() {
        return null;
    }
    
    @Override
    public String nomItemMovible(){
        return "Item";
    }
}
