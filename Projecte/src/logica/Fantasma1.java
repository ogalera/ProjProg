/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import javax.swing.ImageIcon;
import logica.laberints.Laberint;
import logica.enumeracions.EDireccio;
import logica.enumeracions.EElement;
import logica.log.Log;

/**
 *
 * @author oscar
 */
public class Fantasma1 extends Personatge{
    private final Log log;
    private final ImageIcon imatgeFantasma;
    
    public Fantasma1(Partida partida, Laberint laberint, Punt inici, long millis) {
        super(partida, laberint, EElement.FANTASMA1.obtenirImatge(), inici, millis);
        log = Log.getInstance(Fantasma1.class);
        this.imatgeFantasma = EElement.FANTASMA1.obtenirImatge();
    }

    @Override
    public void realitzarMoviment(){
        super.realitzarMoviment();
        partida.assignarPuntsEnemic(punts);
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
