/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.generadors_laberint;

import excepcions.LaberintException;
import java.util.Random;
import log.Log;
import logica.EnumElement;
import logica.Posicio;
import logica.ValidadorLaberint;

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
    private final EnumElement enemic;
    
    public GeneradorLaberintAleatori(int costat, EnumElement enemic){
        if(costat < 5) throw new LaberintException("La mida minima del costat del laberint és 5");
        if(!enemic.esEnemic()) throw new LaberintException("Hi ha de haver un enemic en el laberint");
        this.costat = costat;
        this.enemic = enemic;
    }
    
    @Override
    public EnumElement[][] generarLaberint() {
        Log log = Log.getInstance(GeneradorLaberintAleatori.class);
        log.afegirDebug("Procedim a generar un laberint aleatori de "+costat+"X"+costat);
        long tempsInici = System.currentTimeMillis();
        
        EnumElement [][] tauler = new EnumElement[costat][costat];
        
        int nCandidats = 2;
        Posicio[] candidats = new Posicio[costat*costat];
        candidats[0] = new Posicio(0, 0);
        candidats[1] = new Posicio(costat-1, costat-1);
        int llindar = (int) (costat*costat*0.7);

        Random r = new Random(System.currentTimeMillis());
        
        do{
            nCandidats = 2;
            //Omplim el tauler de parets;
            for(int i = 0; i < costat; i++){
                for(int j = 0; j < costat; j++){
                    tauler[i][j] = EnumElement.PARED;
                }
            }
            
            //Posició [0, 0] hi ha en pacman
            //(extrem superior esquerra)
            tauler[0][0] = EnumElement.PACMAN;

            //Posició [costat-1, costat-1] hi ha l'enemic;
            //(extrem inferior dret)
            tauler[costat-1][costat-1] = enemic;
            
            while(nCandidats <= llindar){
                int x, y;
                do{
                    x = r.nextInt(costat);
                    y = r.nextInt(costat);
                }while(tauler[x][y] != EnumElement.PARED);
                Posicio origen = new Posicio(x, y);
                Posicio desti = distanciaMinima(origen, candidats, nCandidats);
                nCandidats += ferCamiTauler(origen, desti, tauler, candidats, nCandidats);
            }
        }while(!ValidadorLaberint.validarLaberint(tauler, costat));
        
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
    
    private int ferCamiTauler(Posicio origen, Posicio desti, EnumElement[][]tauler, Posicio[] candidats, int nCandidats){
        int incrementX = incrementEix(origen.obtenirX(), desti.obtenirX());
        int incrementY = incrementEix(origen.obtenirY(), desti.obtenirY());
        int nAfegides = 0;
        
        while(!origen.equals(desti)){
            int x = origen.obtenirX();
            int y = origen.obtenirY();
            candidats[nCandidats++] = new Posicio(x, y);
            tauler[x][y] = EnumElement.MONEDA;
            nAfegides++;
            if(x != desti.obtenirX()){
                origen.desplasarX(incrementX);
            }
            else if (y != desti.obtenirY()){
                origen.desplasarY(incrementY);
            }
        }
        return nAfegides;
    }
    
    private int incrementEix(int origen, int desti){
        return origen > desti ? -1: 1;
    }
    
    private Posicio distanciaMinima(Posicio origen, Posicio[] destins, int nDestins){
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
