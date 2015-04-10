/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author oscar
 */
public abstract class ItemMovible {
    private Laberint laberint;
    private Punt posicio;
    private Timer temporitzador;
    private TimerTask tascaAplicarMoviment;
    private static int nItems = 0;
    
    public ItemMovible(Laberint laberint, Punt inici, long millis){
        temporitzador = new Timer("Thread "+nItems++);
        tascaAplicarMoviment = new TimerTask() {
            Punt seguentMoviment;
            @Override
            public void run() {
                realitzarMoviment();
                seguentMoviment = calcularMoviment();
            }
        };
        temporitzador.scheduleAtFixedRate(tascaAplicarMoviment, millis, millis);
    }
    
    public void realitzarMoviment(){
        
    }
    
    protected void canviarFrequenciaMoviment(long millis){
        temporitzador.cancel();
        temporitzador.scheduleAtFixedRate(tascaAplicarMoviment, millis, millis);
    }
    
    public void finalitzarItem(){
        tascaAplicarMoviment.cancel();
        temporitzador.purge();
    }
    
    public abstract Punt calcularMoviment();
}
