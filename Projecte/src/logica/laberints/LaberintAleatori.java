/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.laberints;

import interficie.IPintadorLaberint;
import java.util.Random;
import logica.Partida;
import logica.Punt;
import logica.ValidadorLaberint;
import logica.enumeracions.EDireccio;
import logica.enumeracions.EElement;
import logica.excepcions.ELaberint;
import logica.log.Log;

/**
 *
 * @author oscar
 */
public class LaberintAleatori extends Laberint{
    public LaberintAleatori(Partida partida, int costat, EElement enemic, IPintadorLaberint pintadorLaberint){
        super(partida);
        log = Log.getInstance(LaberintAleatori.class);
        if(costat < 5) throw new ELaberint("La mida minima del costat del laberint és 5");
        if(!enemic.esEnemic()) throw new ELaberint("Hi ha de haver un enemic en el laberint");
        this.costat = costat;
        generarLaberint(enemic);
        this.nMonedes = numeroMonedes();
        this.nMondesPerItem = (int)(nMonedes*0.3);
        this.pintador = pintadorLaberint;
    }
    private EElement[][] generarLaberint(EElement enemic) {
        log.afegirDebug("Procedim a generar un laberint aleatori de "+costat+"X"+costat);
        long tempsInici = System.currentTimeMillis();
        
        tauler = new EElement[costat][costat];
        
        Punt[] candidats = new Punt[costat*costat];
        candidats[0] = new Punt(0, 0);
        candidats[1] = new Punt(costat-1, costat-1);
        int llindar = (int) (costat*costat*0.7);

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
                nCandidats += ferCamiTauler(origen, desti, tauler, candidats, nCandidats);
            }
        }while(!ValidadorLaberint.validarLaberint(tauler, costat));
        
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
    
    private int ferCamiTauler(Punt origen, Punt desti, EElement[][]tauler, Punt[] candidats, int nCandidats){
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
    
    private EDireccio movimentLateral(int origen, int desti){
        if(origen > desti) return EDireccio.ESQUERRA;
        else if(origen < desti) return EDireccio.DRETA;
        return EDireccio.QUIET;
    }
    
    private EDireccio movimentVertical(int origen, int desti){
        if(origen > desti) return EDireccio.AMUNT;
        else if(origen < desti) return EDireccio.AVALL;
        return EDireccio.QUIET;
    }
    
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
