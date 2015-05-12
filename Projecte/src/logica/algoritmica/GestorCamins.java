/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.algoritmica;
import logica.algoritmica.BackTracking.BuscadorCamiMaxim;
import logica.algoritmica.AEstrella.BuscadorCamiMinim;
import logica.laberints.Laberint;
import logica.historic_moviments.HistoricMoviments;
import logica.Punt;
import logica.enumeracions.EDireccio;
/**
 *
 * @author Moises
 */
public class GestorCamins {
    private final BuscadorCamiMinim buscadorDeMinims;
    private final BuscadorCamiMaxim buscadorDeMaxims;
    
    public GestorCamins(Laberint lab){
        buscadorDeMinims = new BuscadorCamiMinim(lab);
        buscadorDeMaxims = new BuscadorCamiMaxim(lab);
    }
    
    public HistoricMoviments trobarCamiMinim(Punt origen, Punt desti){
        return buscadorDeMinims.BuscaCamiMesCurt(origen, desti);
    }
    
    public EDireccio minimitzarDistancia(Punt inici, Punt desti){
        return buscadorDeMinims.BuscaCamiMesCurt(inici, desti).obtenirUltimMoviment();
    }
    
    
    public HistoricMoviments trobarCamiMaximitzarDist(Punt inici, Punt puntAFugir){
        return buscadorDeMaxims.BuscaCamiMaxim(inici, puntAFugir);
    }
    
    
    public EDireccio maximitzarDistancia(Punt inici, Punt puntAFugir){
        return buscadorDeMaxims.BuscaCamiMaxim(inici, puntAFugir).obtenirUltimMoviment();
    }
    
    
}
