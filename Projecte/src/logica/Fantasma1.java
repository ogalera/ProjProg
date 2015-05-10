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
    private final ImageIcon imatgeFantasma;
    
    public Fantasma1(Partida partida, Laberint laberint, Punt inici) {
        super(partida, laberint, EElement.FANTASMA1.obtenirImatge(), inici);
        this.imatgeFantasma = EElement.FANTASMA1.obtenirImatge();
    }

    @Override
    public EElement realitzarMoviment(){
        EElement elementObtingut = super.realitzarMoviment();
        switch(elementObtingut){
            case MONEDA:{
                this.punts+= Utils.Constants.VALOR_MONEDA_NORMAL;
            }break;
            case MONEDA_EXTRA:{
                this.punts+= Utils.Constants.VALOR_MONEDA_EXTRA;
            }break;
        }
        partida.assignarPuntsEnemic(punts);
        return elementObtingut;
    }
    
    @Override
    public EDireccio calcularMoviment() {
        EDireccio moviment;
        Punt p;
        do{
            int index = Utils.obtenirValorAleatori(4);
            moviment = EDireccio.values()[index];
            p = posicio.generarPuntDesplasat(moviment);
        }while(!laberint.posicioValida(p) || laberint.obtenirElement(p) == EElement.PARET);
        return moviment;
    }

    @Override public String nomItemMovible(){
        return "Fantasma1";
    }
}
