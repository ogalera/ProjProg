/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.awt.Image;
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
            case MONGETA:
            case MONEDES_X2:
            case PATINS:{
                partida.itemCapturat();
                assignarEstatPersonatge(elementObtingut);
                partida.assignarItemAEnemic(elementObtingut);
            }
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
        }while(laberint.obtenirElement(p) == EElement.PARET || !laberint.esIntencioValida(p));
        laberint.marcarIntencio(p);
        return moviment;
    }

    @Override public String nomItemMovible(){
        return "Fantasma1";
    }

    @Override
    protected void assignarImatges() {
        int midaImatge = laberint.obtenirMidaImatge();
        this.imatges[0][0] = new ImageIcon(new ImageIcon("res/enemic1D0.png").getImage().getScaledInstance(midaImatge, midaImatge, Image.SCALE_DEFAULT));//EElement.PACMAN.obtenirImatge();
        this.imatges[0][1] = new ImageIcon(new ImageIcon("res/enemic1D1.png").getImage().getScaledInstance(midaImatge, midaImatge, Image.SCALE_DEFAULT));
        this.imatges[1][0] = new ImageIcon(new ImageIcon("res/enemic1E0.png").getImage().getScaledInstance(midaImatge, midaImatge, Image.SCALE_DEFAULT));
        this.imatges[1][1] = new ImageIcon(new ImageIcon("res/enemic1E1.png").getImage().getScaledInstance(midaImatge, midaImatge, Image.SCALE_DEFAULT));
        this.imatges[2][0] = new ImageIcon(new ImageIcon("res/enemic1A0.png").getImage().getScaledInstance(midaImatge, midaImatge, Image.SCALE_DEFAULT));
        this.imatges[2][1] = new ImageIcon(new ImageIcon("res/enemic1A1.png").getImage().getScaledInstance(midaImatge, midaImatge, Image.SCALE_DEFAULT));
        this.imatges[3][0] = new ImageIcon(new ImageIcon("res/enemic1B0.png").getImage().getScaledInstance(midaImatge, midaImatge, Image.SCALE_DEFAULT));
        this.imatges[3][1] = new ImageIcon(new ImageIcon("res/enemic1B1.png").getImage().getScaledInstance(midaImatge, midaImatge, Image.SCALE_DEFAULT));
    }

    @Override
    protected void notificarPerduaEstat() {
        partida.assignarItemAEnemic(EElement.RES);
    }
}
