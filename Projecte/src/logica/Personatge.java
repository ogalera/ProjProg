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
    private EEstatPersonatge estatPersonatge = null;
    protected ImageIcon imatges[][];
    
    protected enum EEstatPersonatge{
        NORMAL, AMB_PATINS, AMB_MONEDES_X2, AMB_MONGETA;
    }
    
    public Personatge(Partida partida, Laberint laberint, ImageIcon imatge, Punt inici) {
        super(imatge, laberint, inici);
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
    
    @Override
    public EElement realitzarMoviment(){
        EElement elementObtingut = laberint.mourePersonatge(posicio, seguentMoviment, imatgePerfil);
        posicio = posicio.generarPuntDesplasat(seguentMoviment);
        return elementObtingut;
    }
    
    protected final void assignarEstatPersonatge(EElement item){
        switch(item){
            case PATINS:{
                estatPersonatge = EEstatPersonatge.AMB_PATINS;
                activarTimerEstat();
                super.canviarFrequenciaMoviment(Utils.Constants.FREQUENCIA_ITEM_MOVIBLE/2);
            }break;
            case MONEDES_X2:{
                if(estatPersonatge == EEstatPersonatge.AMB_PATINS){
                    super.canviarFrequenciaMoviment(Utils.Constants.FREQUENCIA_ITEM_MOVIBLE);
                }
                estatPersonatge = EEstatPersonatge.AMB_MONEDES_X2;
                activarTimerEstat();
            }break;
            case MONGETA:{
                if(estatPersonatge == EEstatPersonatge.AMB_PATINS){
                    super.canviarFrequenciaMoviment(Utils.Constants.FREQUENCIA_ITEM_MOVIBLE);
                }
                estatPersonatge = EEstatPersonatge.AMB_MONGETA;
                activarTimerEstat();
            }break;
        }
    }
    
    public int reiniciarPunts(){
        int p = punts;
        punts = 0;
        return p;
    }
    
    protected void incrementarPunts(int punts){
        if(estatPersonatge == EEstatPersonatge.AMB_MONEDES_X2){
            this.punts+= punts*2;
        }
        else{
            this.punts+=punts;
        }
    }
    
    private void activarTimerEstat(){
        TimerTask timerEstat = new TimerTask() {
            @Override
            public void run() {
                if(estatPersonatge == EEstatPersonatge.AMB_PATINS) {
                    canviarFrequenciaMoviment(Utils.Constants.FREQUENCIA_ITEM_MOVIBLE);
                }
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
