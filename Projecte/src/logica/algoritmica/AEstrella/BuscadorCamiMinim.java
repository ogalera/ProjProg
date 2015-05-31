
package logica.algoritmica.AEstrella;
import logica.algoritmica.Casella;
import logica.algoritmica.LlistaOrdenadaCandidats;
import logica.laberints.Laberint;
import logica.Punt;
import logica.historic_moviments.HistoricMoviments;
import logica.enumeracions.EElement;
import logica.enumeracions.EDireccio;
import logica.excepcions.EBuscadorCamins;


/**
 *
 * @author Moises
 * @brief Classe que implementa l'algorisme AStar per a determinar un 
 * cami minim entre dos punts en un objecte de tipus Laberint.
 */
public class BuscadorCamiMinim {
    private final LlistaOrdenadaCandidats llistaOberta;//!< Conté les caselles que encara hem de processar.
    private final Laberint laberint;//!< Laberint sobre el cual es faran les cerques del camí mínim
    private final Casella [][] tauler; //!< Cada posicio de tauler correspon a una posicio del laberint i contindra la informació heuristica necessaria per a realitzar la cerca del camí mínim.
   
    
    
    public BuscadorCamiMinim(Laberint lab){
        laberint = lab;
        llistaOberta = new LlistaOrdenadaCandidats();

        int mida = lab.obtenirMidaCostatTauler();
        tauler = new Casella  [mida][mida];
        
        for (int i = 0; i < mida; i++){
            for (int j = 0; j < mida; j++){
                tauler[i][j]= new Casella(new Punt(i,j));   
            }
        }
    }
    
    /**
     * 
     * @brief Retorna un camí curt entre origen i desti.
     * @pre origen != null i desti != null.
     * @post Les estructures de dades no contenen informació heurisitica.
     * @return Retorna els moviments necessaris per fer el camí de origen a desti.
     */
    public HistoricMoviments trobaCamiMesCurt(Punt origen, Punt desti){
        buscaCamiMesCurt(origen, desti);
        HistoricMoviments ruta = generaRuta(desti);
        reset();
        return ruta;
    }
    
    
    /**
     * @brief Troba un camí curt desde origen fins desti utilitzant l'algorisme AStar.
     * @pre origen != null i desti != null.
     * @post Les Caselles que forman part del cami curt, tenen com a Casella pare la casella predecessora en el trajecte del cami curt.
     * @throws EBuscadorCamins origen o desti corresponen a un EElement PARET
     */
    private void buscaCamiMesCurt(Punt origen, Punt desti){
        EElement d = laberint.obtenirElement(desti);
        EElement o = laberint.obtenirElement(origen);
        if (d == EElement.PARET || o == EElement.PARET)throw new EBuscadorCamins("Els punts de origen o desti son del tipus PARET");

        
        llistaOberta.clear();
        llistaOberta.afegir(tauler[origen.obtenirFila()][origen.obtenirColumna()]);
        
        Casella actual = llistaOberta.obtenirPrimer();
        while(!llistaOberta.esBuida() && !actual.obtenirPunt().equals(desti)){
            llistaOberta.eliminar(actual);
            actual.processat();
            
            Punt [] adjacents = actual.obtenirPunt().obtenirPosicionsDelVoltant();
            
            for (Punt adjacent : adjacents){
                if (laberint.posicioValida(adjacent)){
                    Casella vei = tauler[adjacent.obtenirFila()][adjacent.obtenirColumna()];
                    if (!vei.haEstatProcessat()){
                        if (!llistaOberta.conteElement(vei)){//No esta a la llista oberta
                            vei.assignarParent(actual);
                            vei.assignarDistanciaAlObjectiu(adjacent.distancia(desti)); 
                            llistaOberta.afegir(vei);
                        }
                        else{//Ja estava a la llista oberta
                            if (vei.obtenirProfunditat() > actual.obtenirProfunditat() +1 ){
                                vei.assignarParent(actual);
                            }

                        }
                    }
                }
            }
            actual = llistaOberta.obtenirPrimer();
        } 
    }
    
    /**
     * @brief Fa un recorregut de Casella fill a Casella pare desde la Casella amb coordenades desti fins al cim de l'arbre (inici). 
     * @param desti La Casella amb coordenades desti es l'ultim node de l'arbre obtingut al fer la cerca del camí mínim.
     * @return Retorna els moviments necessaris per fer el camí obtingut desde origen a desti.
     */
    private HistoricMoviments generaRuta(Punt desti){
        HistoricMoviments ruta = new HistoricMoviments();
        Casella fi = tauler[desti.obtenirFila()][desti.obtenirColumna()];
        Casella seguent = fi.obtenirParent();
        while(seguent != null){
            EDireccio aux = EDireccio.obtenirDireccio(seguent.obtenirPunt(), fi.obtenirPunt());
            ruta.afegirMoviment(aux);
            fi = seguent;
            seguent = seguent.obtenirParent();
        }
        return ruta;
    }
    
    
    /**
     * @brief Esborra tota la informació heuristica de tauler calculada durant el procés de cerca del cami mes curt.
     * @post Els Objectes Casella de tauler no contenen informació heuristica.
     */
    private void reset(){
        int mida = laberint.obtenirMidaCostatTauler();
        for (int i = 0; i < mida; i++){
            for (int j = 0; j < mida; j++){
                 tauler[i][j].reset();
            }
        }
    }
    
    
}
