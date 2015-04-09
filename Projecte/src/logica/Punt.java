/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

/**
 * @author oscar
 * DECLARACIÓ D'INTENCIONS DE LA CLASSE
 * Ens representa un punt en 2D;
 */
public class Punt {
    private int x;
    private int y;
    
    public Punt(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public int obtenirX(){
        return this.x;
    }
    
    public int obtenirY(){
        return this.y;
    }
    
    public void desplasarX(int desplasament){
        this.x += desplasament;
    }
    
    public void desplasarY(int desplasament){
        this.y += desplasament;
    }
    
    public int distancia(Punt other){
        int distancia = Math.abs(this.x-other.x)+Math.abs(this.y-other.y);
        return distancia;
    }
    
    public Punt[] obtenirPosicionsDelVoltant(){
        Punt posicions [] = {new Punt(x-1, y),
                                new Punt(x, y-1),
                                new Punt(x+1, y), 
                                new Punt(x, y+1)};
        return posicions;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj == null || !(obj instanceof Punt)) return false;
        Punt other = (Punt) obj;
        return this.x == other.x && this.y == other.y;
    }
}
