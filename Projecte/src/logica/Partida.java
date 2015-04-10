/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import logica.generadors_laberint.IGeneradorLaberint;

/**
 *
 * @author oscar
 */
public class Partida {
    
    private Laberint laberint;
    private ItemMovible pacman;
    private ItemMovible fantasma;
    
    public Partida(IGeneradorLaberint generadorLaberint){
        laberint = new Laberint(generadorLaberint, this);
        
    }
    
    public void finalitzarPartida(){
        
    }
}
