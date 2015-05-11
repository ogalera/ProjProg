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
import logica.excepcions.EBuscadorCamins;
import logica.log.Log;
import logica.historic_moviments.HistoricMoviments;
import logica.excepcions.EBuscadorCamins;
/**
 *
 * @author Moises
 */
public class Solucio {
    private static final int PROFUNDITAT = 6;
    private Log log;
    private Laberint laberint;
    private Casella [][] tauler;
    private int nivell;
    private final int mida;
    private Punt origen;
    private Punt enemic;
    private final LinkedList<Punt> cami;
    
    public Solucio(Laberint lab, Punt origen, Punt desti){
        log = Log.getInstance(Solucio.class);
        laberint = lab;
        nivell = 0;
        mida = lab.obtenirMidaCostatTauler();
        this.origen = origen;
        this.enemic = desti;
        cami = new LinkedList();

       tauler = new Casella[mida][mida];
        
        for (int i = 0; i < mida; i++){
            for (int j = 0; j < mida; j++){
                tauler[i][j]= new Casella(new Punt(i,j));   
            }
        }
    }
    public Solucio(Solucio s){
        log = s.log;
        laberint = s.laberint;
        nivell = s.nivell;
        mida = s.mida;
        origen = s.origen;
        enemic = s.enemic;
        cami = new LinkedList();
        tauler = new Casella[mida][mida];
        
        for (int i = 0; i < mida; i++){
            for (int j = 0; j < mida; j++){
                tauler[i][j]= new Casella(new Punt(i,j));   
            }
        }
        
        for (int i = 0; i < s.cami.size(); i++){
            cami.addLast(s.cami.get(i));
        }
    }
    
    public Solucio(Laberint lab){
        log = Log.getInstance(Solucio.class);
        laberint = lab;
        nivell = 0;
        mida = lab.obtenirMidaCostatTauler();
        cami = new LinkedList();
        tauler = new Casella[mida][mida];
        
        for (int i = 0; i < mida; i++){
            for (int j = 0; j < mida; j++){
                tauler[i][j]= new Casella(new Punt(i,j));   
            }
        }
    }
    
    public boolean esSolucioCompleta(){
//        boolean esCompleta = nivell >= PROFUNDITAT;
//        if (esCompleta){
//            String s="HE TROBAT UN CAMI!! : \n";
//            for (int i = 0; i < cami.size(); i++){
//                Punt p = cami.get(i);
//                s = s+" "+p+"\n";
//            }
//            log.afegirDebug(s);
//        }
        
        return nivell >= PROFUNDITAT;
    }
    
    public void assignaOrigenIPuntAFugir(Punt origen, Punt enemic){
        //volem maximitzar la distancia entre nosaltres i el enemic
        EElement a = laberint.obtenirElement(origen);
        EElement b = laberint.obtenirElement(enemic);
        if (a == EElement.PARET || b == EElement.PARET)throw new EBuscadorCamins("Origen i/o desti son parets");
        if (a == null || b == null)throw new EBuscadorCamins("Origen i/o desti son nulls");
        this.origen = origen;
        this.enemic = enemic;
    }
    
    public boolean esMillorSolucio(Solucio s){
        int distBuscada= cami.getLast().distancia(enemic);
        int distAntiga = 99;
        if (s!=null)distAntiga = s.cami.getLast().distancia(enemic);
        log.afegirDebug("Nova solucio Trobada: " + distBuscada + " Millor solucio Fins al moment: " + distAntiga);
        return (s == null || cami.getLast().distancia(enemic) > s.cami.getLast().distancia(enemic) );
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
        //laberint.assignaElement(c.obtenirPunt(), EElement.INDEFINIT);
        cami.addLast(c.obtenirPunt());
    }
    public void desanotar(Casella c){
        log.afegirDebug("Desanoto el Candidat: " + c.obtenirPunt());
        cami.removeLast();
        c.noProcessat();
        //laberint.assignaElement(c.obtenirPunt(), EElement.RES);
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
        private EDireccio obtenirMoviment(Punt origen, Punt desti){
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
    
    public HistoricMoviments generaRuta(){
        HistoricMoviments ruta = new HistoricMoviments();
        int midaCami = cami.size();
        for (int i = midaCami -1; i > 0; i--){
            Punt inici = cami.get(i - 1);
            Punt desti = cami.get(i);
            EDireccio aux = obtenirMoviment(inici, desti);
            ruta.afegirMoviment(aux);
        }
        return ruta;
    }
    
    //    public void mostraCami(){
//        int midaVector = cami.size();
//        String s = "EL Cami que t'allunya mes de l'objectiu es: ";
//        for (int i = 0; i < midaVector; i++){
//            Punt p = cami.get(i);
//            s = s + p + "\n";
//        }
//        System.out.print(s);
//    }
    
//    public String toString(){
//        int midaVector = cami.size();
//        String s = "EL Cami que t'allunya mes de l'objectiu es: ";
//        for (int i = 0; i < midaVector; i++){
//            Punt p = cami.get(i);
//            s = s + p + "\n";
//            laberint.assignaElement(p, EElement.INDEFINIT);
//        }
//        log.afegirDebug(s);
//        return laberint.toString();
//    }
}
