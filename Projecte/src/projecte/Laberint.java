/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecte;

import java.util.Random;
import projecte.excepcions.LaberintException;

/**
 *
 * @author oscar
 */
public class Laberint {
    private EnumElement tauler[][];
    
    private Log log = Log.getInstance(Laberint.class);
    
    private int costat = -1;
    
    public Laberint(String fitxer){
        log.afegirDebug("Carreguem un laberint del fitxer "+fitxer);
    }
    
    public Laberint(EnumElement elements[][]){
        this.costat = elements[0].length;
        this.tauler = elements;
    }
    
    public Laberint(int costat, EnumElement fantasma){
        long tIniciCreacio = System.currentTimeMillis();
        if(costat < 4) throw new LaberintException("Tamany del laberint incorrecte: "+costat);
        log.afegirDebug("Creem un laberint de "+costat+"X"+costat);
        this.costat = costat;
        tauler = new EnumElement[costat][costat];
        //Omplim el tauler de parets
        for(int i = 0; i < costat; i++){
            for(int j = 0; j < costat; j++){
                tauler[i][j] = EnumElement.PARED;
            }
        }
        
        //Omplirem el 50% del tauler;
        int elementsAOmplir = (int)(costat*costat*0.5);
        
        tauler[0][0] = EnumElement.PACMAN;
        tauler[costat-1][costat-1] = fantasma;
        
        Random r = new Random(System.currentTimeMillis());
        
        int n = 4;
        Posicio [] tmp = new Posicio[costat*costat];
        tmp[0] = new Posicio(0,1);
        tmp[1] = new Posicio(1,0);
        tmp[2] = new Posicio(costat-1, costat-2);
        tmp[3] = new Posicio(costat-2, costat-1);
        for(int i = 0; i < elementsAOmplir; i++){
            int pos = r.nextInt(n);
            Posicio p = tmp[pos];
            tauler[p.obtenirX()][p.obtenirY()] = EnumElement.MONEDA;
            tmp[pos] = tmp[--n];
            Posicio[] t = p.obtenirPosicionsDelVoltant();
            for(int j = 0; j < 4; j++){
                Posicio c = t[j];
                if(posicioValida(c) && tauler[c.obtenirX()][c.obtenirY()] == EnumElement.PARED){
                    boolean trobat = false;
                    int x = 0;
                    while(x < n && !trobat){
                        if(t[j].equals(tmp[x])) trobat = true;
                        x++;
                    }
                    if(!trobat){
                        tmp[n++] = t[j];
                    }
                }
            }
        }
        
        for(int i = 0; i < costat; i++){
            for(int j = 0; j < costat; j++){
                System.out.print(tauler[i][j].getId()+" ");
            }
            System.out.println();
        }
        long tFiCreacio = System.currentTimeMillis();
        log.afegirDebug("Temps de creacio del laberint "+(tFiCreacio-tIniciCreacio)+ "ms");
    }
    
    /**
     * @pre: --;
     * @post: retornem si la posició és valida dins del tauler;
     * @param posicio: a comparar;
     * @return si la posició està dins del tauler;
     */
    public boolean posicioValida(Posicio posicio){
        int x = posicio.obtenirX();
        int y = posicio.obtenirY();
        return x >= 0 && y >= 0 && x < costat && y < costat;
    }
    
}
