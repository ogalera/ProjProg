/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import log.Log;
import logica.generadors_laberint.IGeneradorLaberint;

/**
 *
 * @author oscar
 */
public class Laberint {
    private EnumElement tauler[][];
    
    private final Log log = Log.getInstance(Laberint.class);
    
    private int costat = -1;
    
    public Laberint(String fitxer, Partida partida){
        log.afegirDebug("Carreguem un laberint del fitxer "+fitxer);
    }
    
    public Laberint(EnumElement elements[][], Partida partida){
        log.afegirDebug("Carreguem un laberint des de una matriu quadrada");
        this.costat = elements[0].length;
        this.tauler = elements;
    }
    
    public Laberint(IGeneradorLaberint generadorLaberint, Partida partida){
        log.afegirDebug("Carreguem un laberint des de un generador de laberints de tipus "+generadorLaberint.getClass().getCanonicalName());
        this.tauler = generadorLaberint.generarLaberint();
        this.costat = tauler[0].length;
        if(!ValidadorLaberint.validarLaberint(this.tauler, costat)){
            
        }
    }
    
    /**
     * @pre: --;
     * @post: retornem si la posició és valida dins del tauler;
     * @param posicio: a comparar;
     * @return si la posició està dins del tauler;
     */
    public boolean posicioValida(Punt posicio){
        int x = posicio.obtenirX();
        int y = posicio.obtenirY();
        return x >= 0 && y >= 0 && x < costat && y < costat;
    }
    
}
