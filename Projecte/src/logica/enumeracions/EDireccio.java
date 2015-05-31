package logica.enumeracions;
import logica.Punt;
import logica.excepcions.ExceptionPunt;

/**
 *
 * @author oscar
 * @brief Enumeracio de les diferents direccions que pot prendre un ItemMobile 
 * per desplaçar-se per el Laberint.
 * @details Laberint es representat per cel·les, on cada cel·la te unes
 * coordenades que son representades per el objecte Punt.
 * 
 *                             COLUMNES
 *     ---------------------------------------------------------------------
 *  F  |  Punt(0,0)  |  Punt (0,1)  |  Punt (0,2) | .........| Punt (0,n-1)|
 *  I  ---------------------------------------------------------------------
 *  L  |  Punt(1,0)  |  Punt (1,1)  |  Punt (1,2) | .........| Punt (1,n-1)|
 *  E  ---------------------------------------------------------------------
 *  S  |  Punt(2,0)  |  Punt (2,1)  |  Punt (2,2) | .........| Punt (2,n-1)|
 *     ---------------------------------------------------------------------
 *     |   ......... | .........    | .........   | ........ | ........... |
 *     ---------------------------------------------------------------------
 *     | Punt(n-1,0) | Punt (n-1,1) | Punt (n-1,2)| ........|Punt (n-1,n-1)| 
 *     ---------------------------------------------------------------------
 * 
 * 0 en les files es la maxima posicio a la esquerra.
 * 0 en les columnes es la maxima posicio amunt.
 * n-1 en les files es la maima posició a la dreta.
 * n-1 en les columnes es la maxima posicio avall.
 * 
 * 
 * Els moviments només s'efectuen en un eix, eix de les files (esquerra i dreta)
 * o eix de les columnes( amunt i avall), es a dir No existeixen moviments 
 * en diagonal, per tant els moviments són amunt, avall, esquerra, dreta, quiet.
 */
public enum EDireccio {
    AMUNT(-1, 0), AVALL(1, 0), ESQUERRA(0, -1), DRETA(0, 1), QUIET(0,0);
    
    private final int incrementColumna;/*!<increment que s'ha d'aplicar a la coordenada
     * columna de un Punt per efectuar un moviemnt en la direccio EDireccio
     * actual, dins de Laberint . */
    private final int incrementFila;/*!< increment que s'ha d'aplicar a la coordenada
     * fila de un Punt per efectuar un moviemnt en la direccio EDireccio
     * actual, dins de Laberint .*/
    
    private EDireccio(int incrementFila, int incrementColumna){
        this.incrementFila = incrementFila;
        this.incrementColumna = incrementColumna;
    }
    
    /**
     * @brief Retorna el increment que s'ha d'aplicar a la coordenada
     * columna de un Punt per efectuar un moviemnt en una direccio EDireccio
     * actual, dins de Laberint .
     */
    public int obtenirIncrementColumna(){
        return incrementColumna;
    }
    
    
    /**
     * @brief Retorna el increment que s'ha d'aplicar a la coordenada
     * fila de un Punt per efectuar un moviemnt en una direccio EDireccio
     * actual, dins de Laberint .
     */
    public int obtenirIncrementFila(){
        return incrementFila;
    }
    
    /**
     * @brief Retorna el moviemt oposat al actual.
     * @return EDireccio oposat al actual.
     */
    public EDireccio obtenirMovimentInvers(){
        EDireccio invers;
        switch(this){
            case AMUNT:{
                invers = AVALL;
            }break;
            case AVALL:{
                invers = AMUNT;
            }break;
            case ESQUERRA:{
                invers = DRETA;
            }break;
            case DRETA:{
                invers = ESQUERRA;
            }break;
            default:{
                invers = QUIET;
            }
        }
        return invers;
    }
    
    /**
     * @brief Donada una EDireccio retorna totes les EDireccions possibles menys EDireccio.QUIET i _direccio.
     * @return Vector que conté totes les EDireccions possibles menys EDireccio.QUIET i _direccio.
     */
    public static EDireccio[] obtenirRestaDireccions(EDireccio _direccio){
        EDireccio [] direccions = null;
        switch(_direccio){
            case DRETA:{
                direccions = new EDireccio[3];
                direccions[0] = DRETA;
                direccions[1] = AMUNT;
                direccions[2] = AVALL;
            }break;
            case ESQUERRA:{
                direccions = new EDireccio[3];
                direccions[0] = ESQUERRA;
                direccions[1] = AMUNT;
                direccions[2] = AVALL;
            }break;
            case AMUNT:{
                direccions = new EDireccio[3];
                direccions[0] = DRETA;
                direccions[1] = ESQUERRA;
                direccions[2] = AMUNT;
            }break;
            case AVALL:{
                direccions = new EDireccio[3];
                direccions[0] = DRETA;
                direccions[1] = ESQUERRA;
                direccions[2] = AVALL;
            }break;
        }
        return direccions;
    }
    
    /**
     * @brief Donats dos punts retorna la direccio correcte per anar de origen a desti. 
     * @pre origen i desti son adjacents
     * @return EDireccio corresponent al desplaçament de origen a desti
     * @exception ExceptionPunt si origen i desti no son adjacents.
     */
    public static EDireccio obtenirDireccio(Punt origen, Punt desti ){
        int diferenciaFila = Math.abs(origen.obtenirFila()-desti.obtenirFila());
        int diferenciaColumna = Math.abs(origen.obtenirColumna() - desti.obtenirColumna());
        
        if (diferenciaFila > 1 || diferenciaColumna > 1)throw new ExceptionPunt("Els punts no son adjacents");
        EDireccio res = EDireccio.QUIET;
        if (origen.obtenirFila() == desti.obtenirFila()){
            if (origen.obtenirColumna() > desti.obtenirColumna()){
                res = EDireccio.ESQUERRA;
            }
            else if (origen.obtenirColumna() < desti.obtenirColumna()){
                res = EDireccio.DRETA;
            }
        }
        else if (origen.obtenirColumna() == desti.obtenirColumna()){
            if(origen.obtenirFila() > desti.obtenirFila()){
                res = EDireccio.AMUNT;
            }
            else if (origen.obtenirFila() < desti.obtenirFila()){
                res = EDireccio.AVALL;
            }
        }
        return res;
    }
}
