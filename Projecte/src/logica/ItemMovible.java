/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import logica.enumeracions.EDireccio;
import logica.excepcions.EItemMovibleIniciat;

/**
 *
 * @author oscar
 */
public abstract class ItemMovible {
    private final Random random;
    private final Timer temporitzador;
    private final TascaAplicarMoviment tascaAplicarMoviment;
    
    protected Laberint laberint;
    protected Punt posicio;
    private static int nItems = 0;
    protected EDireccio seguentMoviment;
    private long frequencia;
    private boolean iniciat = false;
    
    public ItemMovible(Laberint laberint, Punt inici, long frequencia){
        this.laberint = laberint;
        this.posicio = inici;
        this.random = new Random(System.currentTimeMillis());
        this.frequencia = frequencia;
        temporitzador = new Timer("Thread "+this.nomItemMovible());
        
        ++nItems;
        
        seguentMoviment = this.calcularMoviment();
        tascaAplicarMoviment = new TascaAplicarMoviment();
    }
    
    public void iniciarItemMovible(){
        if(iniciat) throw new EItemMovibleIniciat(this.nomItemMovible());
        iniciat = true;
        temporitzador.scheduleAtFixedRate(tascaAplicarMoviment, 0, frequencia);
    }
    
    public void realitzarMoviment(){
        laberint.anotarElement(posicio, seguentMoviment);
        posicio = posicio.generarPuntDesplasat(seguentMoviment);
    }
    
    protected void canviarFrequenciaMoviment(long frequencia){
        this.frequencia = frequencia;
        temporitzador.cancel();
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
    
    protected int obtenirValorAleatori(int max){
        return this.random.nextInt(max);
    }
    
    public abstract EDireccio calcularMoviment();
    public abstract String nomItemMovible();
}
