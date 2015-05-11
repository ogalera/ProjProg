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
    private final EElement tipusElement;
    private EElement elementTrapitjat;
    
    public Item(EElement tipusElement, EElement elementTrapitjat, Laberint laberint, Punt inici){
        super(tipusElement.obtenirImatge(), laberint, inici);
        this.tipusElement = tipusElement;
        this.elementTrapitjat = elementTrapitjat;
        super.iniciarItemMovible();
    }

    @Override
    public EDireccio calcularMoviment(){
        Punt posDesplasada = super.posicio.generarPuntDesplasat(EDireccio.ESQUERRA);
        if(laberint.posicioValida(posDesplasada) && laberint.obtenirElement(posDesplasada) != EElement.PARET){
            return EDireccio.ESQUERRA;
        }
        return EDireccio.QUIET;
    }
    
    @Override
    public String nomItemMovible(){
        return "Item "+tipusElement;
    }

    @Override
    public EElement realitzarMoviment() {
        if(seguentMoviment != EDireccio.QUIET){
            elementTrapitjat = laberint.moureItem(posicio, seguentMoviment, elementTrapitjat);
            posicio = posicio.generarPuntDesplasat(seguentMoviment);
            return elementTrapitjat;
        }
        return null;
    }
}
