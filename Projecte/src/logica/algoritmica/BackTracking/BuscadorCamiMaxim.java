
package logica.algoritmica.BackTracking;
import logica.laberints.Laberint;
import logica.Punt;
import logica.algoritmica.Casella;
import logica.algoritmica.LlistaOrdenadaCandidats;
import logica.log.Log;
import logica.historic_moviments.HistoricMoviments;
/**
 *
 * @author Moises
 * @brief Classe que implementa l'algorisme de Backtracking per a trobar un 
 * camí que maximitzi la distancia desde un punt inici respecte a un punt enemic
 */
public class BuscadorCamiMaxim {
    private final Log log;
    private Solucio opt;//!< Solucio optima
    private final Solucio act;//!< Solucio sobre la que es buscara maximitzar la distancia.
    
    public BuscadorCamiMaxim(Laberint lab){
        log = Log.getInstance(BuscadorCamiMaxim.class);
        act = new Solucio(lab);
    }
    
    /**
     * @brief Retorna el cami amb maxima distancia desde personatge respecte enemic.
     * @param personatge Punt inicial.
     * @param enemic Punt del qual ens volem distanciar.
     * @pre personatge != null, enemic != null
     * @return Retorna els moviments necessaris per a maximitzar la distancia desde personatge respecte a enemic 
     */
    public HistoricMoviments buscaCamiMaxim(Punt personatge, Punt enemic){
        act.reset();
        opt = null;
        act.assignaPuntAFugir(enemic);
        Casella inici = new Casella(personatge);
        if (act.acceptable(inici)){
            act.anotar(inici);
            long tempsInici = System.currentTimeMillis();
            buscarCamiBackTrack(personatge);
            long tempsFi = System.currentTimeMillis();
            log.afegirDebug("Temps a trobar el cami mes llarg utilitzant Backtracking: "+(tempsFi-tempsInici)+"ms");
        }
        return opt.generaRuta();
    }
    
    
    /**
     * @brief Troba la millor solucio al problema de maximitzar la distancia respecte al punt enemic utilitzant l'algorisme de BackTracking
     * @param personatge Punt de inici
     * @pre personatge != null
     * @post opt conté la solucio més optima al problema
     */
    private void buscarCamiBackTrack(Punt personatge){
        LlistaOrdenadaCandidats candidats = act.generarCandidats(personatge);
        while (!candidats.esBuida()){
            Casella candidata = candidats.obtenirUltim();//Obtenim el ultim perque estan ordenat per pes ( distancia al objectiu + profunditat) <-- atribut. profunditat no l'utilitzem en el backTracking
            candidats.eliminar(candidata);                //Ens interesa agafar el que esta mes lluny per podar mes rapid
            if (act.acceptable(candidata) && act.potSerMillor(opt, candidata) ){
                act.anotar(candidata);
                if (!act.esSolucioCompleta()){
                    buscarCamiBackTrack(candidata.obtenirPunt());
                }
                else{
                    if (act.esMillorSolucio(opt)){
                       opt = new Solucio(act);    
                    }
                }
                act.desanotar(candidata);
            }
        }
    }
    
    
    
}


