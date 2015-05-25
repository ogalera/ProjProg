package logica.laberints;

import interficie.IPintadorLaberint;
import logica.Partida;
import logica.enumeracions.EElement;
import logica.excepcions.ELaberint;
import logica.log.Log;

/**
 * @author oscar
 */
public class LaberintLinealHoritzontal extends Laberint{
    public LaberintLinealHoritzontal(Partida partida, int costat, EElement enemic, IPintadorLaberint pintadorLaberint){
        super(partida);
        log = Log.getInstance(LaberintLinealHoritzontal.class);
        if(costat < 5) throw new ELaberint("La mida minima del costat del laberint és 5");
        if(!enemic.esEnemic()) throw new ELaberint("Hi ha de haver un enemic en el laberint");
        if(costat %2 == 0) throw new ELaberint("Els laberints lineals han de tenir un costat senar");
        this.costat = costat;
        this.generarLaberint(enemic);
        this.nMonedes = numeroMonedes();
        this.nMondesPerItem = (int)(nMonedes*0.3);
        this.pintador = pintadorLaberint;
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
        
        for(int fila = 1; fila < costat-1; fila++){
            for(int columna = 0; columna < costat; columna++){
                if(fila %2 == 0 || columna == 0 || columna == costat-1){
                    tauler[fila][columna] = EElement.MONEDA;
                }
                else{
                    tauler[fila][columna] = EElement.PARET;
                }
            }
        }
        
        for(int fila = 0; fila < costat; fila++){
            System.out.println();
            for(int columna = 0; columna < costat; columna++){
                System.out.print(tauler[fila][columna].obtenirLletraRepresentacio()+" ");
            }
        }
        System.out.println();
        long tempsFi = System.currentTimeMillis();
        log.afegirDebug("Temps per generar el laberint: "+(tempsFi-tempsInici)+"ms");
        return tauler;
    }
}