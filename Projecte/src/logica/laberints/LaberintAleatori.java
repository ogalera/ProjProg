package logica.laberints;

import interficie.IPintadorLaberint;
import java.util.Random;
import logica.Partida;
import logica.Punt;
import logica.Utils;
import logica.ValidadorLaberint;
import logica.enumeracions.EDireccio;
import logica.enumeracions.EElement;
import logica.excepcions.ELaberint;
import logica.log.Log;

/**
 * @author oscar
 * @brief
 * Laberint amb camins aleatoris, l'estrategia per generar laberints d'aquest tipus és:
 * Posar en pacman al extrem esquerra superior "casella [0, 0]" i l'enemic especificat
 * en l'extrem inferior dret "casella [N-1, N-1].
 * 
 * Llavors sortejar una casella dins del tauler, buscar la posició habilitada
 * més propera al punt sortejat i traçar un camí, en tot el camí es van posant
 * monedes.
 * 
 * Aquest procés s'ha de repetir fins arribar a un nombre de monedes proporcional
 * a la mida del tauler i mentre el tauler no sigui valid.
 * 
 * Per validar el tauler s'utilitza la classe ValidadorLaberint.java
 */
public class LaberintAleatori extends Laberint{
    
    /**
     * @pre costat > 5 i enemic és Fantasma1, Fantasma2 o Fantasma3
     * @post em cret un labrint aleatori (costat x costat) que forma part de partida, es pintarà a traves
     * de pintadorLaberint i on l'extrem superior esquerra [0, 0] hi ha en pacman 
     * i en l'extrem inferior dret hi ha l'enemic;
     */
    public LaberintAleatori(Partida partida, int costat, EElement enemic, IPintadorLaberint pintadorLaberint){
        super(partida);
        log = Log.getInstance(LaberintAleatori.class);
        if(costat < Utils.Constants.MINIM_COSTAT_LABERINT) throw new ELaberint("La mida minima del costat del laberint és "
                                                                                +Utils.Constants.MINIM_COSTAT_LABERINT);
        if(!enemic.esEnemic()) throw new ELaberint("Hi ha de haver un enemic en el laberint");
        this.costat = costat;
        generarLaberint(enemic);
        
        this.nMonedes = numeroMonedes();
        this.nMondesPerItem = (int)(nMonedes*0.3);
        this.pintador = pintadorLaberint;
        
        int nMonedesExtra = (nMonedes*Utils.Constants.TAN_X_CENT_MONEDES_EXTRA)/100;
        
        ///Un cop generat el laberint sortejem les monedes extra;
        for(int i = 0; i < nMonedesExtra; i++){
            int fila;
            int columna;
            do{
                fila = Utils.obtenirValorAleatori(costat);
                columna = Utils.obtenirValorAleatori(costat);
            }while(tauler[fila][columna] != EElement.MONEDA);
            tauler[fila][columna] = EElement.MONEDA_EXTRA;
        }
    }
    
    /**
     * @pre enemic és Fantasma1, Fantasma2 o Fantasma3
     * @post s'ha generat el laberint aleatori;
     */
    private void generarLaberint(EElement enemic) {
        log.afegirDebug("Procedim a generar un laberint aleatori de "+costat+"X"+costat);
        long tempsInici = System.currentTimeMillis();
        
        tauler = new EElement[costat][costat];
        
        Punt[] candidats = new Punt[costat*costat];
        candidats[0] = new Punt(0, 0);
        candidats[1] = new Punt(costat-1, costat-1);
        int llindar = (int) (costat*costat*Utils.Constants.LLINDAR_MONEDES);

        Random r = new Random(System.currentTimeMillis());
        do{
            int nCandidats = 2;
            //Omplim el tauler de parets;
            for(int i = 0; i < costat; i++){
                for(int j = 0; j < costat; j++){
                    tauler[i][j] = EElement.PARET;
                }
            }
            
            //Posició [0, 0] hi ha en pacman
            //(extrem superior esquerra)
            tauler[0][0] = EElement.PACMAN;

            //Posició [costat-1, costat-1] hi ha l'enemic;
            //(extrem inferior dret)
            tauler[costat-1][costat-1] = enemic;
            
            while(nCandidats <= llindar){
                int fila, columna;
                do{
                    fila = r.nextInt(costat);
                    columna = r.nextInt(costat);
                }while(tauler[fila][columna] != EElement.PARET);
                Punt origen = new Punt(fila, columna);
                Punt desti = distanciaMinima(origen, candidats, nCandidats);
                nCandidats += ferCamiTauler(origen, desti, candidats, nCandidats);
            }
        }while(!ValidadorLaberint.validarLaberint(tauler, costat));
        
        String laberint = "\n";
        for(int i = 0; i < costat; i++){
            for(int j = 0; j < costat; j++){
                laberint += tauler[i][j].obtenirLletraRepresentacio()+" ";
            }
            laberint+="\n";
        }
        log.afegirDebug(laberint+"\n");
        long tempsFi = System.currentTimeMillis();
        log.afegirDebug("Temps per generar el laberint: "+(tempsFi-tempsInici)+"ms");
    }
    
    /**
     * @pre --
     * @post em fet un cami (de monedes) de origen a desti passant per candidats
     * i s'ha retornat el nombre de monedes afegides.
     */
    private int ferCamiTauler(Punt origen, Punt desti, Punt[] candidats, int nCandidats){
        EDireccio movimentLateral = movimentLateral(origen.obtenirColumna(), desti.obtenirColumna());
        EDireccio movimentVertical = movimentVertical(origen.obtenirFila(), desti.obtenirFila());
        int nAfegides = 0;
        while(!origen.equals(desti)){
            int columna = origen.obtenirColumna();
            int fila = origen.obtenirFila();
            candidats[nCandidats++] = new Punt(fila, columna);
            tauler[fila][columna] = EElement.MONEDA;
            nAfegides++;
            //El moviment és en vertical o en horitzontal
            if(columna != desti.obtenirColumna()){
                //En horitzontal
                origen = origen.generarPuntDesplasat(movimentLateral);
            }
            else if (fila != desti.obtenirFila()){
                //En vertical
                origen = origen.generarPuntDesplasat(movimentVertical);
            }
        }
        return nAfegides;
    }
    
    /**
     * @pre --
     * @post em retornat si respecte columna origen i columna desti
     * cal fer un moviment cap a l'esquerra o cap a la dreta.
     */
    private EDireccio movimentLateral(int columnaOrigen, int columnaDesti){
        if(columnaOrigen > columnaDesti) return EDireccio.ESQUERRA;
        else if(columnaOrigen < columnaDesti) return EDireccio.DRETA;
        return EDireccio.QUIET;
    }
    
    /**
     * @pre --
     * @post em retornat si respecte fila origen i fila desti 
     * cal fer un moviment cap amunt o cap avall.
     */
    private EDireccio movimentVertical(int filaOrigen, int filaDesti){
        if(filaOrigen > filaDesti) return EDireccio.AMUNT;
        else if(filaOrigen < filaDesti) return EDireccio.AVALL;
        return EDireccio.QUIET;
    }
    
    /**
     * @pre el nombre de destins > 0.
     * @post em retornat el punt mes proper a origen de l'array destins.
     */
    private Punt distanciaMinima(Punt origen, Punt[] destins, int nDestins){
        int index = 0;
        int distanciaMinima = origen.distancia(destins[0]);
        for(int i = 1; i < nDestins; i++){
            int distancia = origen.distancia(destins[i]);
            if(distancia < distanciaMinima){
                distanciaMinima = distancia;
                index = i;
            }
        }
        return destins[index];
    }
}
