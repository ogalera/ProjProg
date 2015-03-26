/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecte;

/**
 * @author oscar
 * DECLARACIÓ D'INTENCIONS DE LA CLASSE
 * Ens representa un punt en 2D;
 */
public class Posicio {
    private final int x;
    private final int y;
    
    public Posicio(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public int obtenirX(){
        return this.x;
    }
    
    public int obtenirY(){
        return this.y;
    }
    
    public Posicio[] obtenirPosicionsDelVoltant(){
        Posicio posicions [] = {new Posicio(x-1, y),
                                new Posicio(x, y-1),
                                new Posicio(x+1, y), 
                                new Posicio(x, y+1)};
        return posicions;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj == null || !(obj instanceof Posicio)) return false;
        Posicio other = (Posicio) obj;
        return this.x == other.x && this.y == other.y;
    }
}
