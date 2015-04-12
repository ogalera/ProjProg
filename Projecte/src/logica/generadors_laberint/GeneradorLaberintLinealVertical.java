/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.generadors_laberint;

import logica.excepcions.ELaberint;
import logica.log.Log;
import logica.enumeracions.EElement;

/**
 *
 * @author oscar
 */
public class GeneradorLaberintLinealVertical implements IGeneradorLaberint{

    /**
     * EXEMPLE DE LABERINT LINEAL VERTICAL 15 X 15
      
        P O O O O O O O O O O O O O O 
        O X O X O X O X O X O X O X O 
        O X O X O X O X O X O X O X O 
        O X O X O X O X O X O X O X O 
        O X O X O X O X O X O X O X O 
        O X O X O X O X O X O X O X O 
        O X O X O X O X O X O X O X O 
        O X O X O X O X O X O X O X O 
        O X O X O X O X O X O X O X O 
        O X O X O X O X O X O X O X O 
        O X O X O X O X O X O X O X O 
        O X O X O X O X O X O X O X O 
        O X O X O X O X O X O X O X O 
        O X O X O X O X O X O X O X O 
        O O O O O O O O O O O O O O A 
    */
    
    /**
    * Costat del laberint, tot laberint ha de ser quadrat;
    */
    private final int costat;
    
    /**
     * Enemic que estarà en l'extrem inferior dret (casella [costat-1, costat-1])
     */
    private final EElement enemic;
    
    public GeneradorLaberintLinealVertical(int costat, EElement enemic){
        if(costat < 5) throw new ELaberint("La mida minima del costat del laberint és 5");
        if(!enemic.esEnemic()) throw new ELaberint("Hi ha de haver un enemic en el laberint");
        if(costat %2 == 0) throw new ELaberint("Els laberints lineals han de tenir un costat senar");
        this.costat = costat;
        this.enemic = enemic;
    }
    
    @Override
    public EElement[][] generarLaberint() {
        //Cost del algorisme n² on n és el costat del laberint;
        
        Log log = Log.getInstance(GeneradorLaberintLinealVertical.class);
        log.afegirDebug("Procedim a generar un laberint lineal de "+costat+"X"+costat);
        long tempsInici = System.currentTimeMillis();
        EElement [][] tauler = new EElement[costat][costat];
        
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
