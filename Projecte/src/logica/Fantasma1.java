/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import logica.enumeracions.EDireccio;
import logica.enumeracions.EElement;
import logica.log.Log;

/**
 *
 * @author oscar
 */
public class Fantasma1 extends Personatge{
    private final Log log;
    
    public Fantasma1(Laberint laberint, Punt inici, long millis) {
        super(laberint, inici, millis);
        log = Log.getInstance(Fantasma1.class);
    }

    @Override
    public EDireccio calcularMoviment() {
        EDireccio moviment;
        Punt p;
        do{
            int index = super.obtenirValorAleatori(4);
            moviment = EDireccio.values()[index];
            p = posicio.generarPuntDesplasat(moviment);
        }while(!laberint.posicioValida(p) || laberint.obtenirElement(p) == EElement.PARET);
        log.afegirDebug("Nou moviment "+moviment);
        return moviment;
    }

    @Override public String nomItemMovible(){
        return "Fantasma1";
    }
}
