/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import javax.swing.ImageIcon;
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
}
