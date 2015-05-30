
package logica.algoritmica.BackTracking;
import logica.laberints.Laberint;
import logica.algoritmica.Casella;
import logica.Punt;
import logica.enumeracions.EElement;
import logica.enumeracions.EDireccio;
import java.util.LinkedList;
import logica.algoritmica.LlistaOrdenadaCandidats;
import logica.log.Log;
import logica.historic_moviments.HistoricMoviments;
import logica.excepcions.ExceptionBuscadorCamins;
import logica.algoritmica.AEstrella.BuscadorCamiMinim;
/**
 *
 * @author Moises
 * @brief Classe encarregada de guardar els valors de la solució composta per a 
 * l'algorisme de Backtracking
 */
public class Solucio {
    
    private static final int PROFUNDITAT = 7;//!< Nivell de Profunditat al que volem arrivar en el algorisme de Backtracking.
    
    private final Log log;
    private final Laberint laberint;//!< Laberint sobre el cual es faran les cerques del camí maxim
    private final Casella [][] tauler;//!< Cada posicio de tauler correspon a una posicio del laberint i contindra la informació necessaria per a realitzar la cerca del camí maxim.
    private final LinkedList<Punt> cami; //Insercions rapides al principi i al final(no necessito accessos per posicio)
    private final BuscadorCamiMinim buscadorCamiMinim;
    
    private int nivell;
    private Punt enemic; //volem maximitzar la distancia amb Punt enemic
    private int distancia;
    
    public Solucio(Solucio s){
        log = s.log;
        laberint = s.laberint;
        nivell = s.nivell;
        int mida = laberint.obtenirMidaCostatTauler();
        enemic = s.enemic;
        distancia = s.distancia;
        buscadorCamiMinim = s.buscadorCamiMinim;
        cami = new LinkedList<>();
        tauler = new Casella[mida][mida];
        
        for (int i = 0; i < mida; i++){
            for (int j = 0; j < mida; j++){
                tauler[i][j]= new Casella(new Punt(i,j));   
            }
        }
        
        for (Punt p : s.cami) {
            cami.addLast(p);
        }
    }
    
    public Solucio(Laberint lab){
        log = Log.getInstance(Solucio.class);
        laberint = lab;
        nivell = 0;
        buscadorCamiMinim = new BuscadorCamiMinim(lab);
        cami = new LinkedList<>();
        int mida = lab.obtenirMidaCostatTauler();
        tauler = new Casella[mida][mida];
        for (int i = 0; i < mida; i++){
            for (int j = 0; j < mida; j++){
                tauler[i][j]= new Casella(new Punt(i,j));   
            }
        }
    }
    
    /**
     * 
     * @brief Retorna cert si la solució es completa
     */
    public boolean esSolucioCompleta(){
        return nivell >= PROFUNDITAT;
    }
    
    /**
     * @brief Assignacio del punt del cual volem maximitzar la distancia
     * @pre enemic != null
     * @param enemic Punt del qual volem fugir.
     */
    public void assignaPuntAFugir(Punt enemic){
        EElement element = laberint.obtenirElement(enemic);
        distancia = 0;
        if (element == EElement.PARET)throw new ExceptionBuscadorCamins("Origen i/o desti son parets");
        if (element == null)throw new ExceptionBuscadorCamins("Origen i/o desti son nulls");
        this.enemic = enemic;
    }
    
    /**
     * 
     * @brief Diu si solucio actual es millor solucio que s.
     * @param s Solucio a comparar amb objecte actual 
     * @return Retorna cert si Solucio actual es millor solucio que s.
     */
     public boolean esMillorSolucio(Solucio s){
        return (s == null || distancia > s.distancia );
    }
    
    /**
     *
     * @brief Diu si el candidat es acceptable
     * @param c Candidat a la solucio parcial
     * @pre c != null.
     * @return Retorna cert si c encara no ha estat processada
     */ 
    public boolean acceptable(Casella c){
        return !c.haEstatProcessat();
    }
    
    
    /**
     * @brief Anota el candidat c a la solucio actual.
     * @pre c != null
     * @post Afegeix el candidat c a la estructura de dades del objecte actual. Candidat c forma part de la solució. nivell++.
     * @param c Candidat actual a la solucio parcial
     */
    public void anotar (Casella c){
        log.afegirDebug("Anoto el Candidat: " + c.obtenirPunt());
        nivell++;
        c.processat();
        distancia = c.obtenirDistanciaAlObjectiu();
        cami.addLast(c.obtenirPunt());
    }
    
    /**
     * @brief Desanota el candidat c de la solució actual.
     * @pre c != null
     * @post Elimina el candidat c de la estructura de dades del objecte actual. Candidat c no forma part de la solució. nivell--.
     * @param c Candidat c es eliminat de la solucio parcial.
     */
    public void desanotar(Casella c){
        log.afegirDebug("Desanoto el Candidat: " + c.obtenirPunt());
        cami.removeLast();
        Punt ultimMoviment = cami.getLast();
        distancia = tauler[ultimMoviment.obtenirFila()][ultimMoviment.obtenirColumna()].obtenirDistanciaAlObjectiu();
        c.noProcessat();
        nivell--;
    }
  
    /**
     * @brief Retorna cert si afegim el candidat c a la solucio actual i aquesta pot ser millor solucio que opt.
     * @param opt Solucio optima fins al moment
     * @param c Candidat actual
     * @return Retorna cert si la distancia entre c i enemic mes els passos restants, es mes gran que la distancia obtinguda en opt.
     */
    public boolean potSerMillor(Solucio opt, Casella c){
        boolean podriaSerMillor = false;
        if (opt != null){
            int distOptima = opt.distancia; // Distancia de la solucio optima
            int nivellsRestants = PROFUNDITAT - (nivell + 1); //Pasos que hem queden per fer si accepto aquest candidat
            int distEnemic = c.obtenirDistanciaAlObjectiu(); //Distancia al meu enemic si accepto aquest candidat

            //(Si la  distancia desde el candidat actual + els pasos que hem queden per fer) > que la distancia optima --> llavors pot 
            //ser una solucio millor.
            if (distEnemic + nivellsRestants > distOptima) {
                podriaSerMillor = true;
            }
        }
        return (opt == null || podriaSerMillor);
    }
    
    
    /**
     * @brief Retorna les possibles Caselles a les que podem accedir desde la Casella amb coordenades p.
     * @pre p != null. Anteriorment s'ha d'haver assignat un enemic.
     * @param p Punt actual en la construccio del camí màxim.
     * @return Retorna una llista de 0..4 candidats que estan dins del tauler i no son paret, ordenats ascendentment per distancia al enemic.
     */
    public LlistaOrdenadaCandidats generarCandidats(Punt p){
        LlistaOrdenadaCandidats res = new LlistaOrdenadaCandidats();
        Punt up = p.generarPuntDesplasat(EDireccio.AMUNT);
        Punt down = p.generarPuntDesplasat(EDireccio.AVALL);
        Punt left = p.generarPuntDesplasat(EDireccio.ESQUERRA);
        Punt right = p.generarPuntDesplasat(EDireccio.DRETA);
        String s = "Els candidats per el punt "+p+" son: \n";
        if (laberint.obtenirElement(up) != EElement.PARET){
            int dist = buscadorCamiMinim.trobaCamiMesCurt(up, enemic).obtenirMida();
            tauler[up.obtenirFila()][up.obtenirColumna()].assignarDistanciaAlObjectiu(dist);
            res.afegir(tauler[up.obtenirFila()][up.obtenirColumna()]);
            s = s + up + " \n";
        }
        if (laberint.obtenirElement(down) != EElement.PARET){
            int dist =  buscadorCamiMinim.trobaCamiMesCurt(down, enemic).obtenirMida();
            tauler[down.obtenirFila()][down.obtenirColumna()].assignarDistanciaAlObjectiu(dist);
            res.afegir(tauler[down.obtenirFila()][down.obtenirColumna()]);
            s = s + down + " \n";
        }
        if (laberint.obtenirElement(left) != EElement.PARET){
            int dist =  buscadorCamiMinim.trobaCamiMesCurt(left, enemic).obtenirMida();
            tauler[left.obtenirFila()][left.obtenirColumna()].assignarDistanciaAlObjectiu(dist);
            res.afegir(tauler[left.obtenirFila()][left.obtenirColumna()]);
            s = s + left + " \n";
        }
        if (laberint.obtenirElement(right) != EElement.PARET){
            int dist =  buscadorCamiMinim.trobaCamiMesCurt(right, enemic).obtenirMida();
            tauler[right.obtenirFila()][right.obtenirColumna()].assignarDistanciaAlObjectiu(dist);
            res.afegir(tauler[right.obtenirFila()][right.obtenirColumna()]);
            s = s + right + " \n";
        }
        log.afegirDebug(s);
        return res;
    }
    
    
    
    /**
     * @brief Retorna els n Moviments corresponents al cami calculat per l'algorisme de Backtracking. On n = PROFUNDITAT -1 (n caselles, n-1 moviments) 
     * @pre cami no esta buit. 
     */
    public HistoricMoviments generaRuta(){
        HistoricMoviments ruta = new HistoricMoviments();
        int midaCami = cami.size();
        for (int i = midaCami -1; i > 0; i--){
            Punt inici = cami.get(i - 1);
            Punt desti = cami.get(i);
            EDireccio aux = EDireccio.obtenirDireccio(inici, desti);
            ruta.afegirMoviment(aux);
        }
        return ruta;
    }
    
    /**
     * @brief Deixa al objecte actual preperat per a tornar a fer l'algorisme de Backtracking
     * @post Objecte actual reiniciat a estat inicial.
     */
    public void reset(){
        enemic = null;
        cami.clear();
        distancia = 0;
        nivell = 0;
    }
}
