/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import logica.enumeracions.EElement;
import logica.laberints.Laberint;
import logica.historic_moviments.HistoricMoviments;

/**
 *
 * @author oscar
 */
public abstract class Personatge extends ItemMovible{
    protected HistoricMoviments historicMoviments;
    protected int punts;
    protected Partida partida;
    private boolean guanya;
    private final ImageIcon imatge;
    private EEstatPersonatge estatPersonatge = EEstatPersonatge.NORMAL;
    protected ImageIcon imatges[][];
    
    protected enum EEstatPersonatge{
        NORMAL, AMB_PATINS, AMB_MONGETA, AMB_MONEDES_X2;
    }
    
    public Personatge(Partida partida, Laberint laberint, ImageIcon imatge, Punt inici) {
        super(laberint, inici);
        this.imatge = imatge;
        this.partida = partida;
        this.historicMoviments = new HistoricMoviments();
        this.punts = 0;
    }
    
    public int obtenirPunts(){
        return this.punts;
    }
    
    public void assignarGuanya(boolean guanya){
        this.guanya = guanya;
    }
    
    public boolean estaGuanyant(){
        return this.guanya;
    }
    
    public ImageIcon obtenirImarge(){
        return imatge;
    }
    
    @Override
    public EElement realitzarMoviment(){
        EElement elementObtingut = laberint.mourePersonatge(posicio, seguentMoviment, null);
        posicio = posicio.generarPuntDesplasat(seguentMoviment);
        return elementObtingut;
    }
    
    protected final void assignarEstatPersonatge(EElement elementObtingut){
        switch(elementObtingut){
            case PATINS:{
                this.cambiarEstat(EEstatPersonatge.AMB_PATINS);
            }break;
            case MONGETA:{
                this.cambiarEstat(EEstatPersonatge.AMB_MONGETA);
            }break;
            case MONEDES_X2:{
                this.cambiarEstat(EEstatPersonatge.AMB_MONEDES_X2);
            }break;
        }
    }
    
    private void cambiarEstat(EEstatPersonatge nouEstat){
        this.estatPersonatge = nouEstat;
        TimerTask timerEstat = new TimerTask() {
            @Override
            public void run() {
                estatPersonatge = EEstatPersonatge.NORMAL;
            }
        };
        Timer t = new Timer("Thread cambiar estat personatge a normal");
        t.schedule(timerEstat, Utils.Constants.TEMPS_EFECTES_ITEM_MILISEGONS);
    }
    
    protected final EEstatPersonatge obtenirEstatPersonatge(){
        return estatPersonatge;
    }
}
