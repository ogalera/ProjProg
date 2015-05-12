/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 */
public class Solucio {
    
    private static final int PROFUNDITAT = 4;
    
    private final Log log;
    private final Laberint laberint;
    private final Casella [][] tauler;
    private final LinkedList<Punt> cami;
    private final BuscadorCamiMinim buscadorCamiMinim;
    
    private int nivell;
    private Punt origen;
    private Punt enemic;
    private int distancia;
    
    public Solucio(Solucio s){
        log = s.log;
        laberint = s.laberint;
        nivell = s.nivell;
        int mida = laberint.obtenirMidaCostatTauler();
        origen = s.origen;
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
        distancia = 0;
        cami = new LinkedList<>();
        int mida = lab.obtenirMidaCostatTauler();
        tauler = new Casella[mida][mida];
        for (int i = 0; i < mida; i++){
            for (int j = 0; j < mida; j++){
                tauler[i][j]= new Casella(new Punt(i,j));   
            }
        }
    }
    
    public boolean esSolucioCompleta(){
        boolean esCompleta = nivell >= PROFUNDITAT;
        if (esCompleta){
            distancia = buscadorCamiMinim.BuscaCamiMesCurt(cami.getLast(), enemic).size();
        }
        return esCompleta;
    }
    
    public void assignaOrigenIPuntAFugir(Punt origen, Punt enemic){
        //volem maximitzar la distancia entre nosaltres i el enemic
        EElement a = laberint.obtenirElement(origen);
        EElement b = laberint.obtenirElement(enemic);
        distancia = 0;
        if (a == EElement.PARET || b == EElement.PARET)throw new ExceptionBuscadorCamins("Origen i/o desti son parets");
        if (a == null || b == null)throw new ExceptionBuscadorCamins("Origen i/o desti son nulls");
        this.origen = origen;
        this.enemic = enemic;
    }
    
     public boolean esMillorSolucio(Solucio s){
        return (s == null || distancia > s.distancia );
    }
    
    public boolean acceptable(Casella c){
        boolean accept = true;
        if (c.haEstatProcessat())accept = false;
        else{
            EElement element = laberint.obtenirElement(c.obtenirPunt());
            if (element == EElement.PARET){
                accept = false;
                log.afegirDebug("La casella " + c.obtenirPunt() + "Es una paret");
            }
        }
        return accept;
    }
    
    public void anotar (Casella c){
        log.afegirDebug("Anoto el Candidat: " + c.obtenirPunt());
        nivell++;
        c.processat();
        cami.addLast(c.obtenirPunt());
    }
    public void desanotar(Casella c){
        log.afegirDebug("Desanoto el Candidat: " + c.obtenirPunt());
        cami.removeLast();
        c.noProcessat();
        nivell--;
    }
    
//    public boolean potSerMillor(Solucio s, Casella c){
//        boolean podriaSerMillor = false;
//        if (s == null)podriaSerMillor = true;
//        else{
//            //Distancia aconseguida en la solucio Optima en el nivell actual
//            int distSolOptima = s.cami.get(nivell-1).distancia(enemic);
//            //Distancia aconseguida en la actual
//            int dist = cami.get(nivell-1).distancia(enemic);
//            
//            if (dist >= )
//        }
//        
//        return s.nivell == 0 || 
//    }
    
    public LlistaOrdenadaCandidats generarCandidats(Punt p){
        LlistaOrdenadaCandidats res = new LlistaOrdenadaCandidats();
        Punt up = p.generarPuntDesplasat(EDireccio.AMUNT);
        Punt down = p.generarPuntDesplasat(EDireccio.AVALL);
        Punt left = p.generarPuntDesplasat(EDireccio.ESQUERRA);
        Punt right = p.generarPuntDesplasat(EDireccio.DRETA);
        String s = "Els candidats per el punt "+p+" son: \n";
        if (laberint.posicioValida(up)){
            tauler[up.obtenirFila()][up.obtenirColumna()].afegirDistanciaAlObjectiu(up.distancia(enemic));
            res.afegir(tauler[up.obtenirFila()][up.obtenirColumna()]);
            s = s + up + " \n";
        }
        if (laberint.posicioValida(down)){
            tauler[down.obtenirFila()][down.obtenirColumna()].afegirDistanciaAlObjectiu(down.distancia(enemic));
            res.afegir(tauler[down.obtenirFila()][down.obtenirColumna()]);
            s = s + down + " \n";
        }
        if (laberint.posicioValida(left)){
            tauler[left.obtenirFila()][left.obtenirColumna()].afegirDistanciaAlObjectiu(left.distancia(enemic));
            res.afegir(tauler[left.obtenirFila()][left.obtenirColumna()]);
            s = s + left + " \n";
        }
        if (laberint.posicioValida(right)){
            tauler[right.obtenirFila()][right.obtenirColumna()].afegirDistanciaAlObjectiu(right.distancia(enemic));
            res.afegir(tauler[right.obtenirFila()][right.obtenirColumna()]);
            s = s + right + " \n";
        }
        log.afegirDebug(s);
        return res;
    }
    
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
//    private String donamNom(EDireccio dir){
//        String s = "QUIET";
//        switch(dir){
//            case AVALL: s= "AVALL";
//                break;
//            case AMUNT: s="AMUNT";
//                break;
//            case DRETA: s="DRETA";
//                break;
//            case ESQUERRA: s="ESQUERRA";
//                break;
//            default: s="QUIET";
//                break;       
//        }
//        return s;
//    }
    
//    public void mostraCami(){
//        int midaVector = cami.size();
//        String s = "Estic a la posicio: "+ origen + " \n i el Cami que t'allunya mes de l'objectiu es: \n";
//        for (int i = 0; i < midaVector; i++){
//            Punt p = cami.get(i);
//            s = s + p + "\n";
//        }
//        s=s + "FI\n";
//        System.out.print(s);
//    }
    
    public void reset(){
        origen = null;
        enemic = null;
        cami.clear();
        distancia = 0;
    }
}
