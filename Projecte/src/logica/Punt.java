package logica;

import logica.enumeracions.EDireccio;

/**
 * @author oscar
 * DECLARACIÓ D'INTENCIONS DE LA CLASSE
 * Representació de un punt 2D;
 */
public class Punt{
    /**
     * Coordenades en el pla on:
     * x -> ens definexi el valor del eix de les absices;
     * y -> ens defineix el valor del eix de les ordenades;
     */
    private final int x;
    private final int y;
    
    /**
     * @pre: --;
     * @post: definim un punt a traves de les coordenades x i y;
     * @param x valor del eix de les absices;
     * @param y valor del eix de les ordenades;
     */
    public Punt(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    /**
     * @pre: --;
     * @post: em retornat el valor del eix de les absices;
     * @return: valor del eix de les absices;
     */
    public int obtenirX(){
        return this.x;
    }
    
    /**
     * @pre: --;
     * @post: em retornat el valor del eix de les ordenades;
     * @return: valor del eix de les ordenades;
     */
    public int obtenirY(){
        return this.y;
    }
    
    /**
     * @pre: --;
     * @post: retornem el punt obtingut de aplicar un moviment;
     * @param moviment a aplicar;
     * @return punt desplaçat per moviment;
     */
    public Punt generarPuntDesplasat(EDireccio moviment){
        int xDesplasament = moviment.obtenirIncrementEixX();
        int yDesplasament = moviment.obtenirIncrementEixY();
        return new Punt(x+xDesplasament, y+yDesplasament);
    }
    
    /**
     * @pre: --;
     * @post: em retornat el QUADRAT de la distancia amb desti;
     * @param desti: punt de referencia per calcular la distancia;
     * @return: la distancia al quadrat de contra destí;
     */
    public int distancia(Punt desti){
        int distancia = Math.abs(this.x-desti.x)+Math.abs(this.y-desti.y);
        return distancia;
    }
    
    /**
     * @pre: --;
     * @post:em donat les 4 posicions del voltant;
     * @return: les 4 posicions del voltant;
     */
    public Punt[] obtenirPosicionsDelVoltant(){
        Punt posicions [] = {new Punt(x-1, y),
                                new Punt(x, y-1),
                                new Punt(x+1, y), 
                                new Punt(x, y+1)};
        return posicions;
    }
    
    @Override
    public String toString(){
        //Representació de un punt en format [x, y]
        return "["+this.x+", "+this.y+"]";
    }
    
    @Override
    public boolean equals(Object obj){
        //Dos punts són iguals si coincideixen les x i les y;
        if(obj == null || !(obj instanceof Punt)) return false;
        Punt other = (Punt) obj;
        return this.x == other.x && this.y == other.y;
    }
}
