/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import logica.laberints.Laberint;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import logica.enumeracions.EDireccio;
import logica.enumeracions.EElement;
import logica.excepcions.EItemMovibleIniciat;

/**
 *
 * @author oscar
 */
public abstract class ItemMovible {
    private Timer temporitzador;
    private TascaAplicarMoviment tascaAplicarMoviment;
    
    protected final Laberint laberint;
    protected Punt posicio;
    protected EDireccio seguentMoviment;
    private long frequencia;
    private boolean iniciat = false;
    public final ImageIcon imatgePerfil;
    protected final Partida partida;
    
    public ItemMovible(Partida partida, ImageIcon imatge, Laberint laberint, Punt inici){
        this.partida = partida;
        this.imatgePerfil = imatge;
        this.laberint = laberint;
        this.posicio = inici;
        this.frequencia = Utils.Constants.FREQUENCIA_ITEM_MOVIBLE;
        temporitzador = new Timer("Thread "+this.nomItemMovible());
        seguentMoviment = this.calcularMoviment();
        tascaAplicarMoviment = new TascaAplicarMoviment();
    }
    
    public void iniciarItemMovible(){
        if(iniciat) throw new EItemMovibleIniciat(this.nomItemMovible());
        iniciat = true;
        temporitzador.scheduleAtFixedRate(tascaAplicarMoviment, 0, frequencia);
    }
    
    
    protected void canviarFrequenciaMoviment(long frequencia){
        this.frequencia = frequencia;
        temporitzador.cancel();
        tascaAplicarMoviment = new TascaAplicarMoviment();
        temporitzador = new Timer();
        temporitzador.scheduleAtFixedRate(tascaAplicarMoviment, frequencia, frequencia);
    }
    
    public void finalitzarItem(){
        tascaAplicarMoviment.calcularProximMoviment = false;
        tascaAplicarMoviment.cancel();
        temporitzador.purge();
        System.out.println("Thread finalitzat: "+Thread.currentThread().getName());
    }
    
    private class TascaAplicarMoviment extends TimerTask{
        public boolean calcularProximMoviment = true;
        @Override
        public void run() {
            realitzarMoviment();
            if(calcularProximMoviment) seguentMoviment = calcularMoviment();
        }
        
    }
    
    public abstract EElement realitzarMoviment();
    public abstract EDireccio calcularMoviment();
    public abstract String nomItemMovible();
}
