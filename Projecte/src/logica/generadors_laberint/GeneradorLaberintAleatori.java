/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.generadors_laberint;

import logica.excepcions.ELaberint;
import java.util.Random;
import logica.log.Log;
import logica.enumeracions.EElement;
import logica.Punt;
import logica.ValidadorLaberint;
import logica.enumeracions.EDireccio;

/**
 *
 * @author oscar
 */
public class GeneradorLaberintAleatori implements IGeneradorLaberint{
    /**
     *  EXEMPLE DE LABERINT ALEATORI 15 X 15
        P O O O O O X O X X O X O X X 
        X X O O O X O O O O O O O X O 
        O O O O O O X X O X O O O O O 
        O O O O O O O O O O O O O X O 
        O O X O X O X O X X O X X X O 
        X O O X X O O O X O O X X O O 
        X O X X O X X O O O O O O O O 
        X O X O O X X O O O O O O O O 
        X O O O O O X X O X O O X O X 
        O O O O X X O O O O X O O O O 
        O O X O O X O O O O O O O X X 
        O O O O O O O O O O X O O X X 
        O O O O O X O X O O O O X X O 
        O X X O O O X O O O X O O X O 
        O X O O O X X O O X X O O O A 
    */
    
    /**
    * Costat del laberint, tot laberint ha de ser quadrat;
    */
    private final int costat;
    
    /**
     * Enemic que estarà en l'extrem inferior dret (casella [costat-1, costat-1])
     */
    private final EElement enemic;
    
    public GeneradorLaberintAleatori(int costat, EElement enemic){
        if(costat < 5) throw new ELaberint("La mida minima del costat del laberint és 5");
        if(!enemic.esEnemic()) throw new ELaberint("Hi ha de haver un enemic en el laberint");
        this.costat = costat;
        this.enemic = enemic;
    }
    
    @Override
    public EElement[][] generarLaberint() {
        Log log = Log.getInstance(GeneradorLaberintAleatori.class);
        log.afegirDebug("Procedim a generar un laberint aleatori de "+costat+"X"+costat);
        long tempsInici = System.currentTimeMillis();
        
        EElement [][] tauler = new EElement[costat][costat];
        
        int nCandidats = 2;
        Punt[] candidats = new Punt[costat*costat];
        candidats[0] = new Punt(0, 0);
        candidats[1] = new Punt(costat-1, costat-1);
        int llindar = (int) (costat*costat*0.7);

        Random r = new Random(System.currentTimeMillis());
        
        do{
            nCandidats = 2;
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
                int x, y;
                do{
                    x = r.nextInt(costat);
                    y = r.nextInt(costat);
                }while(tauler[x][y] != EElement.PARET);
                Punt origen = new Punt(x, y);
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
        EDireccio movimentLateral = movimentLateral(origen.obtenirX(), desti.obtenirX());
        EDireccio movimentVertical = movimentVertical(origen.obtenirY(), desti.obtenirY());
        int nAfegides = 0;
        while(!origen.equals(desti)){
            int x = origen.obtenirX();
            int y = origen.obtenirY();
            candidats[nCandidats++] = new Punt(x, y);
            tauler[x][y] = EElement.MONEDA;
            nAfegides++;
            //El moviment és en vertical o en horitzontal
            if(x != desti.obtenirX()){
                //En horitzontal
                origen = origen.generarPuntDesplasat(movimentLateral);
            }
            else if (y != desti.obtenirY()){
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
