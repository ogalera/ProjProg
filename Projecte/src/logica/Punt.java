package logica;

import logica.enumeracions.EDireccio;

/**
 * @author oscar
 * @brief
 * Representació de un punt 2D format per fila i columna;
 */
public class Punt{
    ///Coordenades en el pla.
    private final int columna;/**<ens defineix el valor del eix de les absices*/
    private final int fila; /**<ens defineix el valor del eix de les ordenades*/
    
    /**
     * @pre: --;
     * @post: definim un punt a traves de les coordenades fila i columna;
     */
    public Punt(int fila, int columna){
        this.fila = fila;
        this.columna = columna;
    }
    
    /**
     * @pre: --;
     * @post: em retornat el valor del eix de les absices;
     */
    public int obtenirColumna(){
        return this.columna;
    }
    
    /**
     * @pre: --;
     * @post: em retornat el valor del eix de les ordenades;
     */
    public int obtenirFila(){
        return this.fila;
    }
    
    /**
     * @pre: --;
     * @post: retornem el punt obtingut de aplicar un moviment sobre el punt actual;
     */
    public Punt generarPuntDesplasat(EDireccio moviment){
        int xDesplasament = moviment.obtenirIncrementColumna();
        int yDesplasament = moviment.obtenirIncrementFila();
        return new Punt(fila+yDesplasament, columna+xDesplasament);
    }
    
    /**
     * @pre: --;
     * @post: em retornat el QUADRAT de la distancia amb desti;
     */
    public int distancia(Punt desti){
        int distancia = Math.abs(this.columna-desti.columna)+Math.abs(this.fila-desti.fila);
        return distancia;
    }
    
    /**
     * @pre: --;
     * @post:em donat les 4 posicions del voltant del punt;
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
        //Dos punts són iguals si i només si coincideixen les files(y) i les columnes (x).
        if(obj == null || !(obj instanceof Punt)) return false;
        Punt other = (Punt) obj;
        return this.columna == other.columna && this.fila == other.fila;
    }
}
