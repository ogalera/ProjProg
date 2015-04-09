/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.generadors_laberint;

import excepcions.LaberintException;
import log.Log;
import logica.EnumElement;

/**
 *
 * @author oscar
 */
public class GeneradorLaberintLinealHoritzontal implements IGeneradorLaberint{
    
    /**
     *  EXEMPLE DE LABERINT LINEAL HORITZONTAL 15 X 15
     
        P O O O O O O O O O O O O O O 
        O X X X X X X X X X X X X X O 
        O O O O O O O O O O O O O O O 
        O X X X X X X X X X X X X X O 
        O O O O O O O O O O O O O O O 
        O X X X X X X X X X X X X X O 
        O O O O O O O O O O O O O O O 
        O X X X X X X X X X X X X X O 
        O O O O O O O O O O O O O O O 
        O X X X X X X X X X X X X X O 
        O O O O O O O O O O O O O O O 
        O X X X X X X X X X X X X X O 
        O O O O O O O O O O O O O O O 
        O X X X X X X X X X X X X X O 
        O O O O O O O O O O O O O O A
     */
    
    /**
    * Costat del laberint, tot laberint ha de ser quadrat;
    */
    private final int costat;
    
    /**
     * Enemic que estarà en l'extrem inferior dret (casella [costat-1, costat-1])
     */
    private final EnumElement enemic;
    
    public GeneradorLaberintLinealHoritzontal(int costat, EnumElement enemic){
        if(costat < 5) throw new LaberintException("La mida minima del costat del laberint és 5");
        if(!enemic.esEnemic()) throw new LaberintException("Hi ha de haver un enemic en el laberint");
        if(costat %2 == 0) throw new LaberintException("Els laberints lineals han de tenir un costat senar");
        this.costat = costat;
        this.enemic = enemic;
    }

    @Override
    public EnumElement[][] generarLaberint() {
        //Cost del algorisme n² on n és el costat del laberint;
        
        Log log = Log.getInstance(GeneradorLaberintLinealHoritzontal.class);
        log.afegirDebug("Procedim a generar un laberint lineal de "+costat+"X"+costat);
        long tempsInici = System.currentTimeMillis();
        
        EnumElement[][] tauler = new EnumElement[costat][costat];
        for(int i = 0; i < costat; i++){
            tauler[0][i] = EnumElement.MONEDA;
            tauler[costat-1][i] = EnumElement.MONEDA;
        }
        
        tauler[0][0] = EnumElement.PACMAN;
        tauler[costat-1][costat-1] = enemic;
        
        for(int i = 1; i < costat-1; i++){
            for(int j = 0; j < costat; j++){
                if(i %2 == 0 || j == 0 || j == costat-1){
                    tauler[i][j] = EnumElement.MONEDA;
                }
                else{
                    tauler[i][j] = EnumElement.PARED;
                }
            }
        }
        
        for(int i = 0; i < costat; i++){
            System.out.println();
            for(int j = 0; j < costat; j++){
                System.out.print(tauler[i][j].getLletraRepresentacio()+" ");
            }
        }
        System.out.println();
        long tempsFi = System.currentTimeMillis();
        log.afegirDebug("Temps per generar el laberint: "+(tempsFi-tempsInici)+"ms");
        return tauler;
    }
}
