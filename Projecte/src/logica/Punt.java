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
    private final int columna;
    private final int fila;
    
    /**
     * @pre: --;
     * @post: definim un punt a traves de les coordenades x i y;
     * @param fila valor del eix de les absices;
     * @param columna valor del eix de les ordenades;
     */
    public Punt(int fila, int columna){
        this.fila = fila;
        this.columna = columna;
    }
    
    /**
     * @pre: --;
     * @post: em retornat el valor del eix de les absices;
     * @return: valor del eix de les absices;
     */
    public int obtenirColumna(){
        return this.columna;
    }
    
    /**
     * @pre: --;
     * @post: em retornat el valor del eix de les ordenades;
     * @return: valor del eix de les ordenades;
     */
    public int obtenirFila(){
        return this.fila;
    }
    
    /**
     * @pre: --;
     * @post: retornem el punt obtingut de aplicar un moviment;
     * @param moviment a aplicar;
     * @return punt desplaçat per moviment;
     */
    public Punt generarPuntDesplasat(EDireccio moviment){
        int xDesplasament = moviment.obtenirIncrementColumna();
        int yDesplasament = moviment.obtenirIncrementFila();
        return new Punt(fila+yDesplasament, columna+xDesplasament);
    }
    
    /**
     * @pre: --;
     * @post: em retornat el QUADRAT de la distancia amb desti;
     * @param desti: punt de referencia per calcular la distancia;
     * @return: la distancia al quadrat de contra destí;
     */
    public int distancia(Punt desti){
        int distancia = Math.abs(this.columna-desti.columna)+Math.abs(this.fila-desti.fila);
        return distancia;
    }
    
    /**
     * @pre: --;
     * @post:em donat les 4 posicions del voltant;
     * @return: les 4 posicions del voltant;
     */
    public Punt[] obtenirPosicionsDelVoltant(){
        Punt posicions [] = {new Punt(fila, columna-1),
                                new Punt(fila-1, columna),
                                new Punt(fila, columna+1), 
                                new Punt(fila+1, columna)};
        return posicions;
    }
    
    @Override
    public String toString(){
        //Representació de un punt en format [fila, columna]
        return "["+this.fila+", "+this.columna+"]";
    }
    
    @Override
    public boolean equals(Object obj){
        //Dos punts són iguals si coincideixen les x i les y;
        if(obj == null || !(obj instanceof Punt)) return false;
        Punt other = (Punt) obj;
        return this.columna == other.columna && this.fila == other.fila;
    }
}
