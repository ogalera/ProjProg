/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import logica.laberints.Laberint;
import logica.enumeracions.EDireccio;
import logica.enumeracions.EElement;
import logica.enumeracions.EItems;

/**
 *
 * @author oscar
 */
public class Item extends ItemMovible {
    private final EItems tipusElement;
    private EElement elementTrapitjat;
    
    public Item(EItems tipusElement, Laberint laberint, Punt inici){
        super(laberint, inici);
        this.tipusElement = tipusElement;
        elementTrapitjat = null;
    }

    @Override
    public EDireccio calcularMoviment() {
        return null;
    }
    
    @Override
    public String nomItemMovible(){
        return "Item "+tipusElement;
    }

    @Override
    public EElement realitzarMoviment() {
        elementTrapitjat = laberint.moureItem(posicio, seguentMoviment, elementTrapitjat);
        posicio = posicio.generarPuntDesplasat(seguentMoviment);
        return null;
    }
}
