/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import logica.historic_moviments.HistoricMoviments;

/**
 *
 * @author oscar
 */
public abstract class Personatge extends ItemMovible{
    protected HistoricMoviments historicMoviments;
    protected int punts;
    private boolean guanya;
    
    public Personatge(Laberint laberint, Punt inici, long millis) {
        super(laberint, inici, millis);
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
}
