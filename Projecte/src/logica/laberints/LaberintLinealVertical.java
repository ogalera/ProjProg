package logica.laberints;

import interficie.IPintadorLaberint;
import logica.Partida;
import logica.Utils;
import logica.enumeracions.EElement;
import logica.excepcions.ELaberint;
import logica.log.Log;

/**
 * @author oscar
 * @brief
 * Laberint amb camins verticals, l'estrategia per generar laberints d'aquest tipus és:
 * Posar tota una fila de monedes en la fila nùmero 0 i una en la fila nùmero n-1.
 * 
 * llavors situar en pacman en la posició [0, 0] i fer posar tot de monedes a totes
 * les columnes que són un número parell (0, 2, 4 ...) 
 * 
 * L'únic requisit per aquest tipus de laberint és que el seu costat ha de ser imparell
 * per fer quadrar les columnes de paret.
 * 
 * Aquest algorisme genera sempre un laberint valid.
 */
public class LaberintLinealVertical extends Laberint{
    
    /**
     * @pre costat >= Utils.Constants.MINIM_COSTAT_LABERINT i enemic és un dels fantasmes.
     * @post em creat un laberint de caràcter lineal i vertical amb partida, costat
     * enemic i un pintador per representar el laberint gràficament.
     */
    public LaberintLinealVertical(Partida partida, int costat, EElement enemic, IPintadorLaberint pintadorLaberint){
        super(partida);
        log = Log.getInstance(LaberintLinealVertical.class);
        if(costat < Utils.Constants.MINIM_COSTAT_LABERINT) throw new ELaberint("La mida minima del costat del laberint és "
                                                                                    +Utils.Constants.MINIM_COSTAT_LABERINT);
        if(!enemic.esEnemic()) throw new ELaberint("Hi ha de haver un enemic en el laberint");
        if(costat %2 == 0) throw new ELaberint("Els laberints lineals han de tenir un costat senar");
        tauler = generarLaberint(enemic, costat);
        this.costat = costat;
        this.nMonedes = numeroMonedes();
        this.nMondesPerItem = (int)(nMonedes*0.3);
        this.pintador = pintadorLaberint;
    }
    
    /**
     * @pre costat > 0
     * @post em creat i retornat el laberint lineal vertical amb l'enemic en la posició [N-1, N-1]
     * i en pacman en la posició [0, 0]
     */
    private EElement[][] generarLaberint(EElement enemic, int costat) {
        log.afegirDebug("Procedim a generar un laberint lineal de "+costat+"X"+costat);
        long tempsInici = System.currentTimeMillis();
        EElement [][]t = new EElement[costat][costat];
        
        for(int i = 0; i < costat; i++){
            t[0][i] = EElement.MONEDA;
            t[costat-1][i] = EElement.MONEDA;
        }
        
        ///En la primera posició i posem en pacman.
        t[0][0] = EElement.PACMAN;
        ///En l'última posició i posem el fantasma.
        t[costat-1][costat-1] = enemic;
        
        for(int fila = 1; fila < costat-1; fila++){
            for(int columna = 0; columna < costat; columna++){
                if(columna %2 == 0){
                    t[fila][columna] = EElement.MONEDA;
                }
                else{
                    t[fila][columna] = EElement.PARET;
                }
            }
        }
        
        long tempsFi = System.currentTimeMillis();
        log.afegirDebug("Temps per generar el laberint: "+(tempsFi-tempsInici)+"ms");
        String representacioTauler = "";
        for(int fila = 0; fila < costat; fila++){
            System.out.println();
            for(int columna = 0; columna < costat; columna++){
                representacioTauler += t[fila][columna].obtenirLletraRepresentacio()+" ";
            }
        }
        log.afegirDebug(representacioTauler+"\n");
        return t;
    }
}
