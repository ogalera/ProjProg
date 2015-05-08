/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.laberints;

import logica.laberints.Laberint;
import logica.enumeracions.EElement;
import java.util.*;
import java.util.Random;
import logica.Partida;
import logica.Punt;

/**
 *
 * @author Moises
 */
/**@brief Laberint sense carrers sense sortides **/
public class LaberintObert  {
    protected EElement tauler[][];
   
    protected int costat = -1;
    
    protected Partida partida;
    
    protected int nMonedes = 0;
    
    public LaberintObert(int _costat){
        List<Punt> candidats = new ArrayList<Punt>();
        
        costat  = _costat;
        tauler = new EElement[costat][costat];
        omplenaTaulerDeParet();
        tauler[0][0] = EElement.RES;
        candidats.add(new Punt(0,0));
        tauler[costat-1][costat-1] = EElement.RES;
        candidats.add(new Punt(costat-1, costat -1));
        
        Random rnd = new Random();
        while (!candidats.isEmpty())
        {
            Punt p =  candidats.get(rnd.nextInt()*(candidats.size()-1));
            
        }
        
    }
    

    private void omplenaTaulerDeParet(){
        for (int i = 0; i < costat; i++){
            for (int j = 0; j < costat; j++){
                tauler[i][j] = EElement.PARET;
            }
        }
    }
    
    private int numSortides (Punt p){
        int sortides = 0;
            EElement esquerra= obtenirElement(new Punt(p.obtenirColumna()-1,p.obtenirFila()));
            EElement dreta = obtenirElement(new Punt(p.obtenirColumna()+1,p.obtenirFila()));
            EElement amunt= obtenirElement(new Punt(p.obtenirColumna(),p.obtenirFila()-1));
            EElement avall= obtenirElement(new Punt(p.obtenirColumna(),p.obtenirFila()+1));
            if (esquerra == EElement.RES ){
                sortides++;
            }
            if (dreta == EElement.RES ){
                sortides++;
            }
            if (amunt == EElement.RES ){
                sortides++;
            }
            if (avall == EElement.RES ){
                sortides++;
            }
        
        return sortides;
    }
    
    
    
    public boolean posicioValida(Punt posicio){
        int x = posicio.obtenirColumna();
        int y = posicio.obtenirFila();
        return x >= 0 && y >= 0 && x < costat && y < costat;
    }
    
    public EElement obtenirElement(Punt posicio){
        EElement element = EElement.PARET;
        if(posicioValida(posicio)){
           int x = posicio.obtenirColumna();
           int y = posicio.obtenirFila();
           element = tauler[y][x];
        }
        return element;
    }
}


