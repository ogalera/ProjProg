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
    
    private static final int PROFUNDITAT = 7;
    
    private final Log log;
    private final Laberint laberint;
    private final Casella [][] tauler;
    private final LinkedList<Punt> cami; //Insercions rapides al principi i al final(no necessito accessos per posicio)
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
        //distancia = 0;
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
            //distancia = buscadorCamiMinim.BuscaCamiMesCurt(cami.getLast(), enemic).size();
        }
        return nivell >= PROFUNDITAT;
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
        return !c.haEstatProcessat();
    }
    
    public void anotar (Casella c){
        log.afegirDebug("Anoto el Candidat: " + c.obtenirPunt());
        nivell++;
        c.processat();
        distancia = c.obtenirDistanciaAlObjectiu();
        cami.addLast(c.obtenirPunt());
    }
    public void desanotar(Casella c){
        log.afegirDebug("Desanoto el Candidat: " + c.obtenirPunt());
        cami.removeLast();
        Punt ultimMoviment = cami.getLast();
        distancia = tauler[ultimMoviment.obtenirFila()][ultimMoviment.obtenirColumna()].obtenirDistanciaAlObjectiu();
        c.noProcessat();
        nivell--;
    }
  
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
    //Retorna una llista de 0..4 candidats que estan dins del tauler i no son paret
    public LlistaOrdenadaCandidats generarCandidats(Punt p){
        LlistaOrdenadaCandidats res = new LlistaOrdenadaCandidats();
        Punt up = p.generarPuntDesplasat(EDireccio.AMUNT);
        Punt down = p.generarPuntDesplasat(EDireccio.AVALL);
        Punt left = p.generarPuntDesplasat(EDireccio.ESQUERRA);
        Punt right = p.generarPuntDesplasat(EDireccio.DRETA);
        String s = "Els candidats per el punt "+p+" son: \n";
        if (laberint.obtenirElement(up) != EElement.PARET){
            int dist = buscadorCamiMinim.BuscaCamiMesCurt(up, enemic).obtenirMida();
            tauler[up.obtenirFila()][up.obtenirColumna()].afegirDistanciaAlObjectiu(dist);
            res.afegir(tauler[up.obtenirFila()][up.obtenirColumna()]);
            s = s + up + " \n";
        }
        if (laberint.obtenirElement(down) != EElement.PARET){
            int dist =  buscadorCamiMinim.BuscaCamiMesCurt(down, enemic).obtenirMida();
            tauler[down.obtenirFila()][down.obtenirColumna()].afegirDistanciaAlObjectiu(dist);
            res.afegir(tauler[down.obtenirFila()][down.obtenirColumna()]);
            s = s + down + " \n";
        }
        if (laberint.obtenirElement(left) != EElement.PARET){
            int dist =  buscadorCamiMinim.BuscaCamiMesCurt(left, enemic).obtenirMida();
            tauler[left.obtenirFila()][left.obtenirColumna()].afegirDistanciaAlObjectiu(dist);
            res.afegir(tauler[left.obtenirFila()][left.obtenirColumna()]);
            s = s + left + " \n";
        }
        if (laberint.obtenirElement(right) != EElement.PARET){
            int dist =  buscadorCamiMinim.BuscaCamiMesCurt(right, enemic).obtenirMida();
            tauler[right.obtenirFila()][right.obtenirColumna()].afegirDistanciaAlObjectiu(dist);
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
