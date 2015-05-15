/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 */
public class BuscadorCamiMaxim {
    private final Log log;
    private Solucio opt;
    private final Solucio act;
    
    public BuscadorCamiMaxim(Laberint lab){
        log = Log.getInstance(BuscadorCamiMaxim.class);
        act = new Solucio(lab);
    }
    
    
    public HistoricMoviments BuscaCamiMaxim(Punt personatge, Punt enemic){
        act.reset();
        opt = null;
        act.assignaOrigenIPuntAFugir(personatge, enemic);
        Casella inici = new Casella(personatge);
        if (act.acceptable(inici)){
            act.anotar(inici);
            long tempsInici = System.currentTimeMillis();
            buscarCamiBackTrack(personatge);
            long tempsFi = System.currentTimeMillis();
            log.afegirDebug("Temps a trobar el cami mes llarg: "+(tempsFi-tempsInici)+"ms");
        }
        return opt.generaRuta();
    }
    
    private void buscarCamiBackTrack(Punt personatge){
        LlistaOrdenadaCandidats candidats = act.generarCandidats(personatge);
        while (!candidats.esBuida()){
            Casella candidata = candidats.obtenirUltim();//Obtenim el ultim perque estan ordenat per pes ( distancia al objectiu + profunditat) <-- atribut. profunditat no l'utilitzem en el backTracking
            candidats.elimina(candidata);                //Ens interesa agafar el que esta mes lluny per podar mes rapid
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


