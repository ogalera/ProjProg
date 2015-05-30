
package logica.algoritmica;
import logica.algoritmica.BackTracking.BuscadorCamiMaxim;
import logica.algoritmica.AEstrella.BuscadorCamiMinim;
import logica.laberints.Laberint;
import logica.historic_moviments.HistoricMoviments;
import logica.Punt;


/**
 *
 * @author Moises
 * @brief Encarregada de gestionar les cerques de camins sobre un objecte de tipus Laberint.
 */
public class GestorCamins {
    private final BuscadorCamiMinim buscadorDeMinims;
    private final BuscadorCamiMaxim buscadorDeMaxims;
    
    public GestorCamins(Laberint lab){
        buscadorDeMinims = new BuscadorCamiMinim(lab);
        buscadorDeMaxims = new BuscadorCamiMaxim(lab);
    }
    
    
    /**
     * @brief Retorna un camí mínim entre dos punts.
     * @pre origen != null, desti != null.
     * @return Retorna el minim de moviments que cal fer per anar de origen a desti, en un objecte de tipus Laberint.
     */
    public HistoricMoviments trobarCamiMinim(Punt origen, Punt desti){
        return buscadorDeMinims.trobaCamiMesCurt(origen, desti);
    }

     /**
      * @brief Retorna un cami que maximitza la distancia respecte a puntAFugir.
      * @pre inici != null, puntAFugir != null.
      * @param inici Punt de partida
      * @param puntAFugir Punt del qual volem maximitzar la distància
      * @return Retorna n-1 moviments que maximitzan la distancia desde inici respecte puntAFugir. On n = profunditat del backtracking.
      */
    public HistoricMoviments trobarCamiMaximitzarDist(Punt inici, Punt puntAFugir){
        return buscadorDeMaxims.buscaCamiMaxim(inici, puntAFugir);
    } 
}
