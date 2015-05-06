/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.laberints;

import interficie.FLaberint;
import logica.Partida;
import logica.enumeracions.EElement;
import logica.excepcions.ELaberint;
import logica.log.Log;

/**
 *
 * @author oscar
 */
public class LaberintLinealVertical extends Laberint{
    public LaberintLinealVertical(Partida partida, int costat, EElement enemic){
        super(partida);
        log = Log.getInstance(LaberintLinealVertical.class);
        if(costat < 5) throw new ELaberint("La mida minima del costat del laberint és 5");
        if(!enemic.esEnemic()) throw new ELaberint("Hi ha de haver un enemic en el laberint");
        if(costat %2 == 0) throw new ELaberint("Els laberints lineals han de tenir un costat senar");
        this.costat = costat;
        this.generarLaberint(enemic);
        this.nMonedes = numeroMonedes();
        this.pintador = new FLaberint(tauler);
    }
    
    private EElement[][] generarLaberint(EElement enemic) {
        log.afegirDebug("Procedim a generar un laberint lineal de "+costat+"X"+costat);
        long tempsInici = System.currentTimeMillis();
        tauler = new EElement[costat][costat];
        
        for(int i = 0; i < costat; i++){
            tauler[0][i] = EElement.MONEDA;
            tauler[costat-1][i] = EElement.MONEDA;
        }
        
        tauler[0][0] = EElement.PACMAN;
        tauler[costat-1][costat-1] = enemic;
        
        for(int i = 1; i < costat-1; i++){
            for(int j = 0; j < costat; j++){
                if(j %2 == 0){
                    tauler[i][j] = EElement.MONEDA;
                }
                else{
                    tauler[i][j] = EElement.PARET;
                }
            }
        }
        
        for(int i = 0; i < costat; i++){
            System.out.println();
            for(int j = 0; j < costat; j++){
                System.out.print(tauler[i][j].obtenirLletraRepresentacio()+" ");
            }
        }
        System.out.println();
        long tempsFi = System.currentTimeMillis();
        log.afegirDebug("Temps per generar el laberint: "+(tempsFi-tempsInici)+"ms");
        return tauler;
    }
}
